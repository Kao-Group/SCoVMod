#!/bin/julia

using DataFrames
using CSV
using JSON
using Dates
using Distributions
using StatsBase
using Random

#####

include("moveSim_config")
const EPOCH = Date(1970,1,1)
const EPOCH_START = Dates.value((START_DATE - EPOCH) * 2)
const DAYS = Dates.value(END_DATE - START_DATE)

######

### Load data
println("Loading data...")

#commuter network
cnet = DataFrame(CSV.File(CNET_FILE))

#population estimates
pop = DataFrame(CSV.File(POP_FILE))

#generated population
population = DataFrame(CSV.File(OUT_DIR*"population_data.csv"))

if DAMPEN
    mobility = DataFrame(CSV.File(MOBILITY_FILE))
end

function edate(t)
    return EPOCH+Day(div(t,2))
end


######

### Get fast lookup tables for simualtion
print("Generating looukp tables... ")
tic = now()
locs = pop.RefArea

function generate_destination_lookup(population,locs)
    movers = population[.!ismissing.(population.Destination),:]
    lookup = Dict(loc => Array{Int,1}() for loc in locs)
    for r in 1:nrow(movers)
        push!(lookup[movers[r,:Destination]], movers[r,:PersonID])
    end
    return lookup
end
destination_lookup = generate_destination_lookup(population,locs)

function generate_origin_lookup(population)
    return Dict(population[r,:PersonID]=>population[r,:Origin] for r in 1:nrow(population))
end
origin_lookup = generate_origin_lookup(population)

non_movers = population[ismissing.(population.Destination),:PersonID]

if DAMPEN
    mobility_lookup = Dict(mobility[r,1]=>mobility[r,2] for r in 1:nrow(mobility))
else
    mobility_lookup = nothing
end
println(Dates.value(now()-tic)/1000,"s")

#####

### For each day of the simulation
#start day = 0, t_Stamp = EPOCH_START, 2 time intervals per day, handles 1 day per call
function simDay(day, t_stamp, destination_lookup, origin_lookup, non_movers, locs, mobility_lookup)
    println("Generating day ",day)
    ##Initialise data structures
    step_morn = Dict("step"=>t_stamp,"locs"=>[])
    step_eve = Dict("step"=>t_stamp+1,"locs"=>[])
    eve_movers = Dict(loc => Array{Int,1}() for loc in locs)

    ##shuffle non_movers
    non_movers_pool = shuffle(non_movers)
    
    ##Generate movers for each location
    for dest in locs
        #tic = now()
        #sample all morning movers
        morn_movers = destination_lookup[dest]
        l = length(morn_movers) #lambda number of movers
        if STOCH
            ss = rand(Poisson(l)) #sample size
        else
            ss = l
        end
        if DAMPEN
            d = mobility_lookup[edate(t_stamp)] #dampening factor for date
            ss = round(Int, ss * (1 + (d/100))) #dampen sample size
        end
        if TRIM
            ss = rand(Binomial(ss,1/5)) #Trim 4 in 5 moves (on average)
        end
        if ss < l #less than usual
            sampled_movers = sample(morn_movers,ss,replace=false)
        elseif ss == l #usual
            sampled_movers = morn_movers
        else #extra movers required
            ess = ss - l
            sampled_movers = vcat(morn_movers, [pop!(non_movers_pool) for _ in 1:ess])
        end
        #add to output structure
        push!(step_morn["locs"], Dict("loc"=>dest, "ani"=>sampled_movers))
        #collect return movers
        for p in sampled_movers
            push!(eve_movers[origin_lookup[p]], p)
        end
        #println(dest," in ",Dates.value(now()-tic),"ms")
    end

    ##Add return movers to output structure
    for loc in locs
        push!(step_eve["locs"], Dict("loc"=>loc, "ani"=>eve_movers[loc]))
    end

    ##Write to JSON files
    file_morn = open(string(OUT_DIR,"/moveSim_step_",t_stamp,".json"),"w")
    write(file_morn,json(step_morn))
    close(file_morn)
    file_eve = open(string(OUT_DIR,"/moveSim_step_",t_stamp+1,".json"),"w")
    write(file_eve,json(step_eve))
    close(file_eve)
end

### Run sim days (single-core)
for day in 0:DAYS-1
    @time simDay(day, (EPOCH_START+(day*2)), destination_lookup, origin_lookup, non_movers, locs, mobility_lookup)
end

####>>>>TODO<<<<< Parrallelise

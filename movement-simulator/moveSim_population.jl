#!/bin/julia

using DataFrames
using CSV
using Dates

include("moveSim_config")

### Load data
println("Loading data...")

#commuter network
cnet = DataFrame(CSV.File(CNET_FILE))

#population estimates
pop = DataFrame(CSV.File(POP_FILE))

### Construct population table columns
## Required: {PersonID, Origin, Age} + {PersonID, Origin, Age, Worker, Destination}

p = sum(pop.All) #total population

#Initialise DataFrame
ss = repeat([""],p) #Empty string column
bs = repeat([false],p) #Empty Bool column
df = DataFrame(PersonID=1:p, Origin=ss, Age=ss, Worker=bs, Destination=ss)

#Origins
function add_origins!(df,pop)
    areas = nrow(pop)
    i = 1
    for area in 1:areas
        n = pop[area,:All]
        df[i:i+n-1,:Origin] .= pop.RefArea[area]
        i += n
    end
end
println("Origins...")
@time add_origins!(df,pop)
        
#Age
function add_ages!(df,pop)
    n_areas = nrow(pop)
    i = 1
    for area in 1:n_areas
        for age in [:Young,:Adult,:Elderly] #Assumes age cats
            n = pop[area,age]
            df[i:i+n-1,:Age] .= string(age)
            i += n
        end
    end
end
println("Ages...")
@time add_ages!(df,pop)

#Worker 
#Assuming all adults work
println("Workers...")
@time df[df.Age.=="Adult",:Worker] .= true

#Destination
function add_destinations!(df,pop,cnet)
    #Get weights
    #If no weights in file then calculate
    if !(:weightedFromOrigin in names(cnet))
        wfo = by(cnet,:origin, weightedFromOrigin = :numberFromOrigin => x -> x ./ sum(x))
        cnet.weightedFromOrigin = wfo.weightedFromOrigin
    end
    #make weight lookup
    weight = Dict((cnet[i,:origin],cnet[i,:destination])
                  => cnet[i,:weightedFromOrigin] for i = 1:nrow(cnet))
    #get number of workers
    n_workers = sum(pop.Working)
    #initialise worker destiantion array
    worker_dests = Array{String}(undef,n_workers) .= ""
    #assign workers
    n_areas = nrow(pop)
    i = 1
    for orig in 1:n_areas
        i_origin = i
        n_from_origin = pop[orig,:Working]
        for dest in 1:n_areas
            o_d = (pop[orig,:RefArea],pop[dest,:RefArea])
            if (orig != dest) && (o_d in keys(weight))
                n::Int64 = floor(n_from_origin * weight[o_d])
                worker_dests[i:i+n-1] .= pop[dest,:RefArea]
                i += n
            end
        end
        i = i_origin + n_from_origin
        #print(round((i/n_workers)*100,digits=2),"%","\r")
        print(i," / ",n_workers," workers, ",round((i/n_workers)*100,digits=2),"%","\r")
    end
    df[df.Worker,:Destination] = worker_dests
end
                
println("Destinations...")
@time add_destinations!(df,pop,cnet)

println("Writing population files...")
CSV.write(OUT_DIR*"population_data.csv",df)
CSV.write(OUT_DIR*"population.csv",df[:,[:PersonID,:Origin,:Age]])

############################################
# moveSim -- the SCoVMod movement simulator
############################################

moveSim generates a synthetic population and a set of twice-daily
movements to provide spatial input to SCoVMod. More details about the
algorithm and data sources is provided in moveSim_notes.pdf. This
document is a brief guide to configuring and running moveSim.

moveSim is written in the Julia programming language and should be
executable on all platforms for which Julia is available. However, it
has only been tested under Linux. See https://julialang.org/.

moveSIm consists of three files:
 * moveSim_config   	  -- configuration file.
 * moveSim_population.jl  -- generates the synthetic population.
 * moveSim_moves.jl	  -- generates the movement files for SCoVMod.
 

################
# Configuration
################

The data sources and simulation parameters must be set prior to
running.

All configuration is within the file: moveSim_config

An example configuration is provided and the parameters are as
follows:

* Commuter network file: this contains the expected number of daily
  movements between locations, in the following CSV format:

	  origin,       destination,    numberFromOrigin
	  S00088956,    S00088986,      1
	  S00088956,    S00089120,      1
	  S00088956,    S00089121,      8
	  S00088956,    S00089145,      2
	  ...

* Population estimates file: this contains the resident population of
  each location, split by age category. The 'All' field is the total
  population, the 'Working' field is the working population who are
  allowed to move in the simulation (we assume all adults here), then
  the three age categories 'Young', 'Adult', 'Elderly' correspond to
  0-15, 16-64, 65+, in the following CSV format:

  	  RefArea,	All,	Working, Young,	Adult,	Elderly
	  S00088956,	230,	142,	 71,	142,	17
	  S00088957,	66,	46,	 8,	46,	12
	  S00088958,	90,	54,	 30,	54,	6
	  ...

* Mobility file: this contains the percentage of baseline mobility to
  reduce movement by for each date, in the following CSV format:

	  date,		mobility
	  2020-03-14,	-5.03507625272331
	  2020-03-15,	-2.11372549019608
	  2020-03-16,	-6.4974128540305
	  2020-03-17,	-14.9189814814815
	  2020-03-18,	-20.025462962963
	  ...
	  
  Be sure that the dates correspond exactly to the range of dates to
  be simulated.

* Output location: directory into which the output will be saved, and
  from which moveSim_moves.jl will load the population file.

* Simulation start/end dates: in the format "Date(YYYY,MM,DD)", but
  with no leading zeros, e.g. "Date(2020,1,3)" for the 3rd Jan.

* DAMPEN: if true then the mobility values will be applied to the
  simulation.

* TRIM: if true then an optimisation is applied removing 4/5
  movements.

* STOCH: if true the simulation is stochastic, if false the expected
  number of moves is made each day (subject to damping if DAMPEN is
  set).


#############
# Execution
#############

Once the configuration file has been set up, to run the simulation do:

     $ julia moveSim_population.jl

This will generate the population_data.csv file, which is input to the
next step, and the population.csv file, which is input to SCoVMod.

Then do:

     $ julia moveSim_moves.jl

This will generate the JSON movement files for SCoVMod. There will be
two files for each day of the simulation.

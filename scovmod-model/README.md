## SCoVMod

Please note - a more descriptive document for the model will be created at a later date.
This document has some notes on the model inputs.

The data for scovmod takes the following directory structure

SUMMARYSTAT
JSON_MOVEMENTS
SEEDING
HEALTH

In the SUMMARYSTAT directory there is as follows: observations.csv and
OA_info_for_movement_model.csv

(A) The observations file (the actual real life observed statistics) is
used for fitting and has the following format (part example):

date,area,measurement,value
2020-03-09,15,DEAD,0
2020-03-09,16,DEAD,0
2020-03-09,17,DEAD,0
2020-03-09,19,DEAD,0
2020-03-09,20,DEAD,0
2020-03-09,22,DEAD,0
2020-03-09,24,DEAD,0
2020-03-09,25,DEAD,0
2020-03-09,26,DEAD,0


(1) Currently there is only one summary statistic (measurement) - DEAD.

(2) In our case the area is the Council Area  - of which there are 32.

(3) The starting date of the model is 2020-03-09 and we have weekly
values for DEAD for every council area (CA). The final week is 2020-04-06.

(4) When there are no deaths - we still record it as zero.

(B) The OA_info_for_movement_model.csv is used as a way of linking
different levels of scale - it is read into the model to be able to
perform lookups. In the model we have used OA and HB. This means if the
locations and movements are at OA level it is possible to aggregate up
to the HB level - which is necessary for fitting. It has the following
format (part example):

thomdoherty@ranch-010:~/_CODE/SCoVMod-model/data/SUMMARYSTAT$ head
OA_info_for_movement_model.csv
output_area,intermediate_zone,super_layer,workplace_zone,local_authority_name,local_authority,health_board_name,health_board,easting,northing,HH_population,WF_population,young_population,working_age_population,old_population
S00097336,S02000168,S01007538,S34000893,Dumfries and
Galloway,S12000006,Dumfries and
Galloway,S08000017,280641,556236,75,44,12,48,22
S00089523,S02000033,S01006688,S34003046,Aberdeen
City,S12000033,Grampian,S08000020,392008,808104,101,39,9,47,27

In the JSON_MOVEMENTS directory there is as JSON directory that holds as
follows - for example files called moveSim_step_36614.json and
moveSim_step_36615.json

We have half day timesteps for day and night. Here is the format (part
example) of a movement file.
{
   "step" : 991,
   "locs" : [ {
    "loc" : 1234,
     "ani" : [ 103583530, 103537950]
   }  } ]
}
In the above example in timestep 991 the ‘ani array shows the
‘personIDs’ of the people who commuted to location/community 1234 as a
movement that day. Return movement to home community in next time step.

In the SEEDING directory there is as follows:
spatialSeedingGroupAttributes.csv and population.csv

The spatialSeedingGroupAttributes.csv is how the seeds (we fit number of
seeds as a parameter)  are distributed among the council area.

The format looks as follows:

group,weight
15,0.0375
16,0.04375
17,0.04375
19,0.0625
20,0.075
22,0.0125
24,0.175
25,0.0
26,0.06875
28,0.0
29,0.04375
30,0.09375
31,0.24375
32,0.1

This is based on how the test positives were spread among the CA's on
the 15th March (slight tweak to fix Dumfries and Galloway):

https://github.com/watty62/Scot_covid19/blob/master/data/processed/regional_cases.csv

The population.csv is created elsewhere along with the movement files.

The format looks as follows (part example):

PersonID,Area,Age
1,S00097336,Young
2,S00097336,Young
3,S00097336,Young

Where PersonID is the same as 'ani' in the move files (sorry should have
changed that), Area is the OA the person lives in (or whatever level you
want to run the model at as long as it can aggregate up to the HB level
and is available in the lookup table mentioned previously). The
population is also split along demographic lines - so we read in the the
age group here also - to be used for covid specific parameters later.
The age groups are Young, Adult and Elderly. Only Adults commute to
work. The other age groups are pre and post working age. This file size
should equal the population. For example 5.5M for Scotland.

In the HEALTH directory there is as follows: deprivationIndex.csv

This deprivationIndex.csv file has the following format:

CA,index
15,28.28449377
16,15.72185475
17,16.80101126
19,17.70649213
20,10.84380362
22,14.53387911
24,16.29550089
25,10.59653685
26,10.86340133
28,13.72908302
29,18.76111476
30,18.28639984
31,31.76163256
32,26.56907092

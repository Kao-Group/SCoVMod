import pandas as pd


# read the household populations of each OA

# get OAs and their corresponding coordinates
df=pd.read_csv('../data/scotland_oa_2011.csv')
#print(df.columns)
OA_list=df['code'].tolist()
HH_population=df['hhcount'].tolist()

# get a dictionary for the poulations of the OAs
total_population={}
while OA_list:
    OA=OA_list.pop()
    HH_pop=HH_population.pop()
    total_population[OA]=HH_pop

# get the super_layer populations
df=pd.read_csv('../output/population_estimates.csv')
code=df['code'].tolist()
young=df['Children (under 16 years)'].tolist()
working=df['Working Age (16 - 64)'].tolist()
old=df['Pensionable Age (65 and over)'].tolist()

young_population={}
working_population={}
old_population={}

while code:
    SL=code.pop()
    young_population[SL]=young.pop()
    working_population[SL]=working.pop()
    old_population[SL]=old.pop()
    
#get the Super_layers
df=pd.read_csv('../output/OA_SL_membership/OA_SL_membership.csv')
print(df.head())

super_layers=list(set(df['super_layer'].tolist()))


# dictionary to store results
OA_old_population={}
OA_young_population={}
OA_working_population={}
dic={'output_area':[],
     'young_population':[],
     'working_age_population':[],
     'old_population':[]
     }


# loop over the Super layers 
for SL in super_layers:
    # get the population by age 
    young=young_population[SL]
    working=working_population[SL]
    old=old_population[SL]
    
    # get all the OAs in the SL
    SL_df=df[(df['super_layer']==SL)]
    OA_list=SL_df['output_area'].tolist()
    
    total_OA_population_for_SL=sum([total_population[OA] for OA in OA_list])
    
    # for each OA calculate the estimation for each age group
    for OA in OA_list:
        proportion_in_OA=total_population[OA]/total_OA_population_for_SL
        dic['output_area'].append(OA)
        dic['young_population'].append(round(young*proportion_in_OA))
        dic['working_age_population'].append(round(working*proportion_in_OA))
        dic['old_population'].append(round(old*proportion_in_OA))

df=pd.DataFrame(dic)
df.to_csv('../output/OA_population_by_age.csv',index=False)


import pandas as pd

dic={}

# get OAs and their corresponding coordinates
df=pd.read_csv('../data/scotland_oa_2011.csv')

OA_list=df['code'].tolist()

# Esting and northing HH population
easting={}
northing={}
HH_population={}
for i,row in df.iterrows():
    OA=row['code']
    easting[OA]=row['easting']
    northing[OA]=row['northing']
    HH_population[OA]=row['popcount']

# get the workforce 
df=pd.read_excel('../data/WP101SCoa.xlsx',names=['output_area','number_of_workers'],skiprows=5).dropna()

WF_population={}
for i,row in df.iterrows():
    OA=row['output_area']
    WF_population[OA]=int(row['number_of_workers'])

# get the age-specific populations 
df=pd.read_csv('../output/OA_population_by_age.csv')

old_population={}
working_age_population={}
young_population={}
for i,row in df.iterrows():
    OA=row['output_area']
    old_population[OA]=row['old_population']
    working_age_population[OA]=row['working_age_population']
    young_population[OA]=row['young_population']


# get the LAD
LAD={}
df=pd.read_csv('../output/OA_LAD_membership/OA_LAD_membership.csv')
for i,row in df.iterrows():
    OA=row['output_area']
    LAD[OA]=row['local_authority']
    
#get the HB names    
LAD_NAME={}
df=pd.read_csv('../output/LAD_names/LAD_names.csv')
for i,row in df.iterrows():
    code=row['code']
    LAD_NAME[code]=row['name'] 

#get the HB names    
HB_NAME={}
df=pd.read_csv('../output/HB_names/HB_names.csv')
for i,row in df.iterrows():
    code=row['code']
    HB_NAME[code]=row['name']
    

# get the CHP
HB={}
df=pd.read_csv('../output/OA_HB_membership/OA_HB_membership.csv')
for i,row in df.iterrows():
    OA=row['output_area']
    HB[OA]=row['health_board']

# get the IZ
IZ={}
df=pd.read_csv('../Output/OA_IZ_membership/OA_IZ_membership.csv')
for i,row in df.iterrows():
    OA=row['output_area']
    IZ[OA]=row['intermediate_zone']

#get the SL
SL={}
df=pd.read_csv('../output/OA_SL_membership/OA_SL_membership.csv')
for i,row in df.iterrows():
    OA=row['output_area']
    SL[OA]=row['super_layer']

#get the WZ
WZ={}
df=pd.read_csv('../Output/OA_WZ_membership/OA_WZ_membership.csv')
for i,row in df.iterrows():
    OA=row['output_area']
    WZ[OA]=row['workplace_zone']
    
dic={'output_area':[],
     'intermediate_zone':[],
     'super_layer':[],
     'workplace_zone':[],
     'local_authority_name':[],
     'local_authority':[],
     'health_board_name':[],
     'health_board':[],
     'easting':[],
     'northing':[],
     'HH_population':[],
     'WF_population':[],
     'young_population':[],
     'working_age_population':[],
     'old_population':[]}

for OA in OA_list:
    if OA in LAD and OA in WF_population:
        dic['output_area'].append(OA)
        dic['intermediate_zone'].append(IZ[OA])
        dic['super_layer'].append(SL[OA])
        dic['workplace_zone'].append(WZ[OA])
        dic['local_authority_name'].append(LAD_NAME[LAD[OA]])
        dic['local_authority'].append(LAD[OA])
        dic['health_board_name'].append(HB_NAME[HB[OA]])
        dic['health_board'].append(HB[OA])
        dic['easting'].append(easting[OA])
        dic['northing'].append(northing[OA])
        dic['HH_population'].append(HH_population[OA])
        dic['WF_population'].append(WF_population[OA])
        dic['young_population'].append(young_population[OA])
        dic['working_age_population'].append(working_age_population[OA])
        dic['old_population'].append(old_population[OA])

df=pd.DataFrame(dic)
df.to_csv('../output/OA_info_for_movement_model.csv',index=False)

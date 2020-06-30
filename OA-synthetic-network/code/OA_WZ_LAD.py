# -*- coding: utf-8 -*-
"""
Created on Mon Mar 30 15:29:48 2020

@author: Ewan
"""
import pandas as pd

df=pd.read_csv('../output/postcode_spreadsheet.csv')

#LADs
LAD_list=list(set(df['oslaua'].tolist()))
print(LAD_list)
#print(len(set(df['oa11'])))

dic={'output_area':[],'local_authority':[]}

# OAs
for LAD in LAD_list:
    print(LAD)
    LAD_df=df[(df['oslaua']==LAD)]
    #get list of all the different output areas with that LAD    
    objectid_list=list(set(LAD_df['oa11']))

    dic['output_area']=dic['output_area']+objectid_list
    dic['local_authority']=dic['local_authority']+[LAD for i in range(len(objectid_list))]

# Do same for workplace zones


new_df=pd.DataFrame(dic) 
print(new_df.head())

new_df.to_csv('../output/OA_LAD_membership/OA_LAD_membership.csv',index=False)



# Do same for workplace zones
WZs
WZ_list=list(set(df['wz11'].tolist()))


dic={'output_area':[],'workplace_zone':[]}

count=0
for WZ in WZ_list:
    count=count+1
    if count % 500==0:
        print(count)
        
    WZ_df=df[(df['wz11']==WZ)]
    #get list of all the different output areas with that LAD    
    objectid_list=list(set(WZ_df['oa11']))

    dic['output_area']=dic['output_area']+objectid_list
    dic['workplace_zone']=dic['workplace_zone']+[WZ for i in range(len(objectid_list))]

new_df=pd.DataFrame(dic) 
print(new_df.head())

new_df.to_csv('../output/OA_WZ_membership/OA_WZ_membership2.csv',index=False)


# now do WZ to LAD

dic={'workplace_zone':[],'local_authority':[]}

# OAs
for LAD in LAD_list:
    print(LAD)
    LAD_df=df[(df['oslaua']==LAD)]
    #get list of all the different output areas with that LAD    
    objectid_list=list(set(LAD_df['wz11']))

    dic['workplace_zone']=dic['workplace_zone']+objectid_list
    dic['local_authority']=dic['local_authority']+[LAD for i in range(len(objectid_list))]

# Do same for workplace zones

new_df=pd.DataFrame(dic) 
print(new_df.head())

new_df.to_csv('../output/WZ_LAD_membership/WZ_LAD_membership.csv',index=False)


# IZs
dic={'output_area':[],'intermediate_zone':[]}
IZ_list=list(set(df['msoa01'].tolist()))
for IZ in IZ_list:
    IZ_df=df[(df['msoa01']==IZ)]
    #get list of all the different output areas with that LAD    
    objectid_list=list(set(IZ_df['oa11']))

    dic['output_area']=dic['output_area']+objectid_list
    dic['intermediate_zone']=dic['intermediate_zone']+[IZ for i in range(len(objectid_list))]

new_df=pd.DataFrame(dic) 
new_df.to_csv('../output/OA_IZ_membership/OA_IZ_membership.csv',index=False)


# SLs
dic={'output_area':[],'super_layer':[]}

SL_list=list(set(df['lsoa11'].tolist()))

for SL in SL_list:
    
    SL_df=df[(df['lsoa11']==SL)]
    #get list of all the different output areas with that LAD    
    objectid_list=list(set(SL_df['oa11']))

    dic['output_area']=dic['output_area']+objectid_list
    dic['super_layer']=dic['super_layer']+[SL for i in range(len(objectid_list))]

new_df=pd.DataFrame(dic) 
print(new_df.head())

#CHPs
dic={'output_area':[],'health_board':[]}

HB_list=list(set(df['oshlthau'].tolist()))

for HB in HB_list:
    
    HB_df=df[(df['oshlthau']==HB)]
    #get list of all the different output areas with that LAD    
    objectid_list=list(set(HB_df['oa11']))

    dic['output_area']=dic['output_area']+objectid_list
    dic['health_board']=dic['health_board']+[HB for i in range(len(objectid_list))]

new_df=pd.DataFrame(dic) 
print(new_df.head())

new_df.to_csv('../output/OA_HB_membership/OA_HB_membership.csv',index=False)






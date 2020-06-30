import pandas as pd
import numpy.random as rdm

df=pd.read_csv('../Output/OA_info_for_movement_model.csv')

IZ_list=list(set(df['intermediate_zone'].tolist()))

OAs_in_IZ={}
for IZ in IZ_list:
    IZ_df=df[(df['intermediate_zone']==IZ)]
    OAs_in_IZ[IZ]=list(set(IZ_df['output_area']))
    
# worker and household densities will be needed for each OA
worker_density={}
household_density={}
northing={}
easting={}
for i,row in df.iterrows():
    worker_density[row['output_area']]=row['WF_population']
    household_density[row['output_area']]=row['working_age_population']
    northing[row['output_area']]=row['northing']
    easting[row['output_area']]=row['easting']
#
###### NEEDS EDITING TO READ IN DOWNLOADABLE DATA ######
###  WU01SC_IZ2011_Scotland  ###########################
#
##df=pd.read_csv('../wu01uk_scotland_intermediate_areas_(ewan_edit).csv')
##print(df.head())

#df=pd.read_excel('../WU01SC_IZ2011_Scotland.xlsx',sheet_name='Persons')
df=pd.read_csv('../output/wu01uk_scotland_IZ.csv')

#IZ_list2=list(set(df['Area of Usual Residence'].tolist()+df['Area of Workplace'].tolist()))

# the final putput will be a df of this dictionary 
dic={'household_OA':[],'workplace_OA':[]}

# loop over every origin destination combo
for index, row in df.iterrows():
    if index % 1000==0:
        print(index)
    
    
    
#    # check that its in the list of IZs for Scotland
#    if row['origin'] in IZ_list and row['destination'] in IZ_list:
    IZ_workers=row['moves']
    #print(row['origin'],row['destination'])
    # for every working individual we individual 
    # we asign an OA for their household 
    # and an OA for their workplace

    # get the potential workplace OAs for the folks in the destination ML
    workplace_candidates=OAs_in_IZ[row['destination']]
    # need the total desnity of workers to do this
    total_workers=sum([worker_density[OA] for OA in workplace_candidates])

    # get their potential household OAs in the origin ML
    household_candidates=OAs_in_IZ[row['origin']]

    # one worker at a time
    while IZ_workers:
        # step 1 choose their workplace OA
        # with probability proportional to OA worker density
               
        # to select with probability proportional to the worker density
        r=rdm.random()
        slider=0
        j=0
        while r>slider:
            workplace_OA=workplace_candidates[j]
            slider=slider+(worker_density[workplace_OA]/total_workers)
            j=j+1

        # get the location
        y_j=northing[workplace_OA]
        x_j=easting[workplace_OA]
        
        # step 2 choose their household
        
        # calculate the distance-population function to all the candidates
        total_weight=0
        weight={}
        # 
        for household_OA in household_candidates:
            #N=household_density[household_OA]
            # get the location
            y_i=northing[workplace_OA]
            x_i=easting[workplace_OA]
            #calcualte the distance
            distance=(((x_i-x_j)**2)+(y_i-y_j)**2)**(1/2)
            
            #weight[household_OA]=N*((1+distance)**(-3))
            total_weight=total_weight+household_density[household_OA]

        # then choose proportionally to the weight
        r=rdm.random()
        slider=0
        i=0
        while r>slider:
            household_OA=household_candidates[i]
            slider=slider+(household_density[household_OA]/total_weight)
            i=i+1        
        
        # add results to the dictionary
        dic['household_OA'].append(household_OA)
        dic['workplace_OA'].append(workplace_OA)
        
        # remove the person from household OA and the workplace OA
        household_density[household_OA]=household_density[household_OA]-1
        #worker_density[workplace_OA]=worker_density[workplace_OA]-1
        # remove one worker as they now have a workpplace and household
        IZ_workers=IZ_workers-1

#save results
df=pd.DataFrame(dic) 
df.to_csv('../output/OA_synthetic_movements.csv',index=False)


        
        
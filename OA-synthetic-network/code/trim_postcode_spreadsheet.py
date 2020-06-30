import pandas as pd



df=pd.read_csv('../data/ONSPD_FEB_2020_UK.csv')

#print(len(df))

#df=pd.DataFrame({'a':['S12000034',2,'S12000021'],'b':[2,3,4]})

# LAD 
LAD_list=['S'+str(12000000+i) for i in range(5,51)]
#
#print(LAD_list)

scot_df=pd.DataFrame(columns=df.columns)
#
for LAD in LAD_list:
    print(LAD)
    LAD_df=df[(df['oslaua']==LAD)]    
    scot_df=pd.concat([scot_df,LAD_df])
    print(len(LAD_df))
    print()

scot_df.to_csv('../output/postcode_spreadsheet.csv',index=False)

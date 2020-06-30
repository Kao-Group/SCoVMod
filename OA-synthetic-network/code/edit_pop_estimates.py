import pandas as pd

# Read in the poopulation estimates by age

df=pd.read_csv('../data/population-estimates-current-geographic-boundaries.csv')
print(df.head())



dic={}
columns=df.columns

for col in columns:
    dic[col]=df[col].tolist()

#change the name of the key
dic['code'] = dic.pop(columns[0])

print(dic.keys())

new_code=[]
for code in dic['code']:
    new_code.append(code[-9:])
    
dic['code']=new_code


df=pd.DataFrame(dic)

#df=df[(df['code'][0:3]=='S01')]

print(df.head())
df.to_csv('../Output/population_estimates.csv',index=False)

import pandas as pd
import numpy.random as rdm

df=pd.read_csv('../data/wu01uk_msoa_v2.csv',dtype=str,names=['origin','destination','moves','other1','other2'])


origin=df['origin'].tolist()
destination=df['destination'].tolist()
moves=df['moves'].tolist()


dic={'origin':[],'destination':[],'moves':[]}
n=0
while origin:
    n=n+1
    if n % 100000==0:
        print(n)
    
    i=origin.pop()
    j=destination.pop()
    m=moves.pop()
    #print(i,j,m)
    if i[0]=='S' and j[0]=='S':
        dic['origin'].append(i)
        dic['destination'].append(j)
        dic['moves'].append(m)
        
# the final putput will be a df of this dictionary 

#save results
df=pd.DataFrame(dic) 
df.to_csv('../output/wu01uk_scotland_IZ.csv',index=False)


        
        
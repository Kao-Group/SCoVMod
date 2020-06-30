import pandas as pd

df=pd.read_csv('../output/OA_synthetic_movements.csv')
print(df.head())

origin_list=df['household_OA'].tolist()
destination_list=df['workplace_OA'].tolist()

#edge weight
weight={}

# strength is total weight of outgoing edges
strength={}

print(len(origin_list))

while origin_list:
    origin=origin_list.pop()
    destination=destination_list.pop()

    if origin in strength:
        strength[origin]=strength[origin]+1
    else:
        strength[origin]=1

    if (origin,destination) in weight:
        weight[(origin,destination)]=weight[(origin,destination)]+1
    else:
        weight[(origin,destination)]=1

dic={'origin':[], 'destination':[], 'numberFromOrigin':[], 'weightedFromOrigin':[]}

# numberFromOrigin is the number moving from origin to destination, 
# weightedFromOrigin = numberFromOrigin / total number of movers from origin.

for (origin,destination) in weight:

    dic['origin'].append(origin)
    dic['destination'].append(destination)
    dic['numberFromOrigin'].append(weight[(origin,destination)])
    dic['weightedFromOrigin'].append(weight[(origin,destination)]/strength[origin])

df=pd.DataFrame(dic) 
df.to_csv('../output/OA_weighted_movement_network.csv',index=False)
#

        
        
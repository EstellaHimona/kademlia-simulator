import csv
import matplotlib.pyplot as graph
import numpy as nmp
import seaborn as sbr
import pandas as pd


# Access the .csv file
# with open ('messages.csv') as file:
#     reader = csv.reader(file)

#     next(reader)

#     x = []
#     y = []

#     for row in reader:
#         # if (row[3] == "MSG_RESPONSE"):
#         # if (row[3].startswith("UN")):
#             x.append(float(row[0]))
#             y.append(float(row[1]))

#     sbr.lineplot(x , y , data = reader)            

df = pd.read_csv('messages.csv')

sbr.lineplot(x = 'dst', y = 'src', data = df )

# sbr.histplot(data = df, x = 'dst', bins=10 )

# # Calculate the slope 
# m, b = nmp.polyfit(x, y, 1)
# print ("The inclination is: ", m)

# Plot the data on a 2D graph
# graph.plot(x,y)
graph.xlabel('Destination')
graph.ylabel('Source')
graph.title('Kademlia')
graph.show()


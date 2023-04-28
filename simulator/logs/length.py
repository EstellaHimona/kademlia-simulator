import csv 

input_file = "messages.csv"
output_file = "length.csv"
component_name = "0.1"

with open(input_file, 'r') as f:
    line_count = sum(1 for line in f)

with open(output_file, 'a', newline='') as f:
    writer = csv.writer(f)
    writer.writerow([component_name, line_count])     

print("Number of lines in file:", line_count)    
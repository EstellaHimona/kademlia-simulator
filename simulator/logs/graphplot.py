import csv 

input_file = "messages.csv"
output_file = "length.csv"
malicious_percentage = "0.0"

with open(input_file, 'r') as f:
    line_count = sum(1 for line in f)

with open(output_file, 'a', newline='') as f:
    writer = csv.writer(f)
    writer.writerow([malicious_percentage, line_count])     

print("Number of lines in file:", line_count)    

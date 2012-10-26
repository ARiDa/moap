# COMMUNITY REPORT

a = read.table("~/Documents/workspace/mobilitynetwork/trunk/network_analysis/location_4h_150m_commreport.csv",sep=",",header=TRUE)
head(a)
cor(a)
f = table((a$avg_time))
plot(f,type="o")
hist(a$avg_distance)
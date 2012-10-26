library("linkcomm")

net = read.table("~/Documents/workspace/mobilitynetwork/trunk/networks/location_4h_150m.csv",sep=" ",header=FALSE)
head(net)

lc = getLinkCommunities(net,plot=FALSE,hcmethod="average")
print(lc)


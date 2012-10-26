-- COMMUNITY REPORT: FRAMEWORK PARA IDENTIFICAR PROPRIEDADES DAS COMUNIDADES

------------------------------------------------------------------------------------------------------------------------
-- TRAJECTORY REPORT
select community,count(*)
from
(
select distinct c.comm community,t.id user_id,t.trip trip_id 
from trip_8h_150m t, location_cluster$ l, location_8h_150m_nodecomm$ c
where t.id2 = l.id 
and l.clus::text = c.node
order by c.comm,t.id,t.trip
) a
group by community
order by community

------------------------------------------------------------------------------------------------------------------------
-- USER REPORT
select community,count(*)
from
(
select distinct c.comm community,t.id user_id 
from trip_8h_150m t, location_cluster$ l, location_8h_150m_nodecomm$ c
where t.id2 = l.id 
and l.clus::text = c.node
order by c.comm,t.id
) a
group by community
order by community

------------------------------------------------------------------------------------------------------------------------
-- EDGES REPORT
select c1.comm,count(*)
from location_8h_150m_edge$ e, location_8h_150m_nodecomm$ c1, location_8h_150m_nodecomm$ c2
where e.source = c1.node and e.target = c2.node and c1.comm=c2.comm
group by c1.comm

------------------------------------------------------------------------------------------------------------------------
-- NODE REPORT
select comm,count(*)
from location_8h_150m_nodecomm$
group by comm

------------------------------------------------------------------------------------------------------------------------
-- WEIGHT REPORT
select c1.comm,avg(weight)
from location_8h_150m_edge$ e, location_8h_150m_nodecomm$ c1, location_8h_150m_nodecomm$ c2
where e.source = c1.node and e.target = c2.node and c1.comm=c2.comm
group by c1.comm

------------------------------------------------------------------------------------------------------------------------
-- POI REPORT
select comm,sum(a.count) count
from location_8h_150m_nodecomm$,
(select clus,count(*) count from location_cluster$ group by clus) a
where a.clus::text = node
group by comm

------------------------------------------------------------------------------------------------------------------------
-- TYPE OF POI REPORT
select comm,count(*)
from
(
select distinct comm,general
from location_8h_150m_nodecomm$,
(select distinct clus,general from location_cluster$) a
where a.clus::text = node
) a
group by comm

------------------------------------------------------------------------------------------------------------------------
-- DISTANCIA ESPACIAL ENTRE OS NODES
SELECT c1.comm,(avg(st_distance(st_transform(c1.cen,26986),st_transform(c2.cen,26986))))/1000 avg_distance
from
(select clus,st_centroid(st_union(object)) cen, nc.comm
from location_cluster$ c, location_8h_150m_nodecomm$ nc
where c.clus::text = nc.node
group by clus,nc.comm) c1,
(select clus,st_centroid(st_union(object)) cen, nc.comm
from location_cluster$ c, location_8h_150m_nodecomm$ nc
where c.clus::text = nc.node
group by clus,nc.comm) c2
where c1.comm = c2.comm and c1.clus < c2.clus
group by c1.comm
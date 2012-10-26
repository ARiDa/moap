-- ANALISAR OS PONTOS DE INTERESSES DOS CLUSTERS ENCONTRADOS

------------------------------------------------------------------------------------------------------------------------------------------------
-- IN-DEGREE, NUMERO DE TRIPS (e.weightS), NUMERO MEDIO DE TRIPS (AVG e.weight)
select l.clus,l.id,l.description,n.indegree,a.n_trip,a.avg_n_trip,users,avg_users
from location_cluster$ l, location_4h_150m_node$ n,
(
select n.id,count(*) count,sum(e.e.weight) n_trip,avg(e.e.weight) avg_n_trip, sum(e.users) users,avg(e.users) avg_users
from location_4h_150m_node$ n,location_4h_150m_edge$ e
where n.id = e.target
group by n.id
) a
where l.clus::text = n.id and n.id=a.id
order by n.indegree desc,l.clus

------------------------------------------------------------------------------------------------------------------------------------------------
-- OUT-DEGREE, NUMERO DE TRIPS (e.weightS), NUMERO MEDIO DE TRIPS (AVG e.weight)
select l.clus,l.id,l.description,n.outdegree,a.n_trip,a.avg_n_trip,users,avg_users
from location_cluster$ l, location_4h_150m_node$ n,
(
select n.id,count(*) count,sum(e.weight) n_trip,avg(e.weight) avg_n_trip , sum(e.users) users,avg(e.users) avg_users
from location_4h_150m_node$ n,location_4h_150m_edge$ e
where n.id = e.source
group by n.id
) a
where l.clus::text = n.id and n.id=a.id
order by n.outdegree desc,l.clus

------------------------------------------------------------------------------------------------------------------------------------------------
-- DEGREE, NUMERO DE TRIPS (e.weightS), NUMERO MEDIO DE TRIPS (AVG e.weight)
select l.clus,l.id,l.description,n.degree,a.n_trip,a.avg_n_trip,users,avg_users
from location_cluster$ l, location_4h_150m_node$ n,
(
select n.id,count(*) count,sum(e.weight) n_trip,avg(e.weight) avg_n_trip , sum(e.users) users,avg(e.users) avg_users
from location_4h_150m_node$ n,location_4h_150m_edge$ e
where n.id = e.source or n.id = e.target
group by n.id
) a
where l.clus::text = n.id and n.id=a.id
order by n.degree desc,l.clus


------------------------------------------------------------------------------------------------------------------------------------------------
-- WEIGHT, NUMERO DE TRIPS (e.weightS), NUMERO MEDIO DE TRIPS (AVG e.weight)
select l.clus,l.id,l.description,n.degree,a.n_trip,a.avg_n_trip,users,avg_users
from location_cluster$ l, location_4h_150m_node$ n,
(
select n.id,count(*) count,sum(e.weight) n_trip,avg(e.weight) avg_n_trip , sum(e.users) users,avg(e.users) avg_users
from location_4h_150m_node$ n,location_4h_150m_edge$ e
where n.id = e.source or n.id = e.target
group by n.id
) a
where l.clus::text = n.id and n.id=a.id
order by a.n_trip desc,l.clus

------------------------------------------------------------------------------------------------------------------------------------------------
-- IN-WEIGHT, NUMERO DE TRIPS (e.weightS), NUMERO MEDIO DE TRIPS (AVG e.weight)
select l.clus,l.id,l.description,n.indegree,a.n_trip,a.avg_n_trip,users,avg_users
from location_cluster$ l, location_4h_150m_node$ n,
(
select n.id,count(*) count,sum(e.weight) n_trip,avg(e.weight) avg_n_trip , sum(e.users) users,avg(e.users) avg_users
from location_4h_150m_node$ n,location_4h_150m_edge$ e
where e.target = n.id
group by n.id
) a
where l.clus::text = n.id and n.id=a.id
order by a.n_trip desc,l.clus

------------------------------------------------------------------------------------------------------------------------------------------------
-- OUT-WEIGHT, NUMERO DE TRIPS (e.weightS), NUMERO MEDIO DE TRIPS (AVG e.weight)
select l.clus,l.id,l.description,n.outdegree,a.n_trip,a.avg_n_trip,users,avg_users
from location_cluster$ l, location_4h_150m_node$ n,
(
select n.id,count(*) count,sum(e.weight) n_trip,avg(e.weight) avg_n_trip , sum(e.users) users,avg(e.users) avg_users
from location_4h_150m_node$ n,location_4h_150m_edge$ e
where n.id = e.source 
group by n.id
) a
where l.clus::text = n.id and n.id=a.id
order by a.n_trip desc,l.clus

------------------------------------------------------------------------------------------------------------------------------------------------
-- BETWEENESS, NUMERO DE TRIPS (e.weightS), NUMERO MEDIO DE TRIPS (AVG e.weight)
select l.clus,l.id,l.description,n.betweenesscentrality,a.n_trip,a.avg_n_trip,users,avg_users
from location_cluster$ l, location_4h_150m_node$ n,
(
select n.id,count(*) count,sum(e.weight) n_trip,avg(e.weight) avg_n_trip , sum(e.users) users,avg(e.users) avg_users
from location_4h_150m_node$ n,location_4h_150m_edge$ e
where n.id = e.source or n.id = e.target -- DEGREE
group by n.id
) a
where l.clus::text = n.id and n.id=a.id
order by n.betweenesscentrality desc,l.clus

------------------------------------------------------------------------------------------------------------------------------------------------
-- CLOSENESS, NUMERO DE TRIPS (e.weightS), NUMERO MEDIO DE TRIPS (AVG e.weight)
select l.clus,l.id,l.description,n.closnesscentrality,a.n_trip,a.avg_n_trip,users,avg_users
from location_cluster$ l, location_4h_150m_node$ n,
(
select n.id,count(*) count,sum(e.weight) n_trip,avg(e.weight) avg_n_trip , sum(e.users) users,avg(e.users) avg_users
from location_4h_150m_node$ n,location_4h_150m_edge$ e
where n.id = e.source or n.id = e.target -- DEGREE
group by n.id
) a
where l.clus::text = n.id and n.id=a.id
order by n.closnesscentrality asc,l.clus

------------------------------------------------------------------------------------------------------------------------------------------------
-- OVERLAPS, NUMERO DE TRIPS (e.weightS), NUMERO MEDIO DE TRIPS (AVG e.weight)
-- TABELA COM COMMUNITY CENTRALITY: location_4h_150m_cc$
select l.clus,l.id,l.description,cc.communitycentrality,c.count,a.n_trip,a.avg_n_trip,users,avg_users
from location_cluster$ l, location_4h_150m_node$ n,
(
select n.id,count(*) count,sum(e.weight) n_trip,avg(e.weight) avg_n_trip , sum(e.users) users,avg(e.users) avg_users
from location_4h_150m_node$ n,location_4h_150m_edge$ e
where n.id = e.source or n.id = e.target -- DEGREE
group by n.id
) a
where l.clus::text = n.id and a.id = l.clus::text
order by n.communitycentrality desc

------------------------------------------------------------------------------------------------------------------------------------------------
-- TIME, NUMERO DE TRIPS (e.weightS), NUMERO MEDIO DE TRIPS (AVG e.weight)
select l.clus,l.id,l.description,n.avg_time,a.n_trip,a.avg_n_trip,users,avg_users
from location_cluster$ l, location_4h_150m_node$ n,
(
select n.id,count(*) count,sum(e.weight) n_trip,avg(e.weight) avg_n_trip , sum(e.users) users,avg(e.users) avg_users
from location_4h_150m_node$ n,location_4h_150m_edge$ e
where n.id = e.source or n.id = e.target -- DEGREE
group by n.id
) a
where l.clus::text = n.id and n.id=a.id
order by n.avg_time asc
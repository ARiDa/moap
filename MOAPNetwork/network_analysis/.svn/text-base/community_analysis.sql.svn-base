-- LISTAR OS TIPOS DE LOCAIS POR COMUNIDADE COM A PORCENTAGEM DOS LUGARES


select n.comm,n.general,count(*)
from location_8h_150m_nodecomm$ n, location_cluster$ c
where c.clus::text = n.node
group by n.comm,n.general

SELECT c.node_community community_id, n.node_label,l.description ,n.node_time, (count(*)/s.locations::numeric)*100 percentege
	FROM community_stats_0 s, community_00 c, location_network_0_node n, location l
	where s.community = c.node_community and c.node_id = n.node_id and l.id = n.node_locationid::integer and s.trajectories > 20 and s.locations > 10
	group by c.node_community,n.node_label,l.description ,n.node_time,s.locations
	--having (count(*)/s.locations::numeric)*100 > 
	order by c.node_community,n.node_label ,n.node_time,s.locations
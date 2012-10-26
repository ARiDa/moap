
-- Number of trips
select count(*) count
from
(select distinct id,trip from trip_4h_150m) a
select ad.airport_code, count(*) as pasajeros 
from boarding_passes bp 
join flights f on bp.flight_id = f.flight_id 
join airports_data ad 
on ad.airport_code = f.arrival_airport
group by ad.airport_code
order by pasajeros
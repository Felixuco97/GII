select flight_id, count(*) as asientos_vacios from
((select f.flight_id, s.seat_no from flights f join seats s on
f.aircraft_code = s.aircraft_code)
EXCEPT
(select f.flight_id, bp.seat_no from flights f join boarding_passes bp on
f.flight_id = bp.flight_id)) as s
group by flight_id
having count(*) = 402
order by asientos_vacios desc
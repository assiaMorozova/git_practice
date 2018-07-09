
select Id_Direction,Direction_Name,Faculty_Name
from Direction,Faculty
where Ref_Faculty=Id_Faculty
order by Faculty_Name,Direction_Name
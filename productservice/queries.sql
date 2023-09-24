select u1_0.id, u1_0.clazz_, u1_0.email, u1_0.name, u1_0.average_rating, u1_0.attendance, u1_0.psp
from (select id, email, name, null as average_rating, null as attendance, null as psp, 0 as clazz_
      from tbc_user
      union all
      select id, email, name, average_rating, null as attendance, null as psp, 1 as clazz_
      from tbc_mentor
      union all
      select id, email, name, null as average_rating, attendance, psp, 2 as clazz_
      from tbc_student
      union all
      select id, email, name, average_rating, null as attendance, null as psp, 3 as clazz_
      from tbc_ta) u1_0
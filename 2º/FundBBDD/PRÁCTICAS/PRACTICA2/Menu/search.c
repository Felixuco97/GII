/*
 * Created by roberto on 3/5/21.
 */
#include "search.h"
#include "odbc.h"
#include <sql.h>
#include <sqlext.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#define NUM_STRINGS 35000
#define STRING_LEN 1000

int results_search(char *from, char *to, char *date,
                   int *n_choices, char ***choices,
                   int max_length,
                   int max_rows)
/**here you need to do your query and fill the choices array of strings
 *
 * @param from form field from
 * @param to form field to
 * @param n_choices fill this with the number of results
 * @param choices fill this with the actual results
 * @param max_length output win maximum width
 * @param max_rows output win maximum number of rows
 */
{
  SQLHENV env;
  SQLHDBC dbc;
  SQLHSTMT stmt;
  SQLRETURN ret; /* ODBC API return status */
  SQLCHAR y[1000];
  SQLCHAR z[1000];
  SQLCHAR v[1000];
  SQLCHAR w[1000];
  SQLCHAR s[1000];
  SQLCHAR u[1000];
  char query[2048];
  char aux_date[32];
  int i = 0;
  int t = 0;
  char *query_result_set[NUM_STRINGS];
  for (i = 0; i < NUM_STRINGS; i++)
  {
    query_result_set[i] = (char *)malloc(STRING_LEN * sizeof(char));
  }

  /* CONNECT */
  ret = odbc_connect(&env, &dbc);
  if (!SQL_SUCCEEDED(ret))
  {
    return EXIT_FAILURE;
  }

  strcpy(aux_date, date);

  for (i = 0; i < strlen(aux_date); i++) {
        if (aux_date[i] == '/') {
            aux_date[i] = '-';
        }
    }

  strcat(aux_date, " 00:00:00");

  /* Allocate a statement handle */
  SQLAllocHandle(SQL_HANDLE_STMT, dbc, &stmt);

  sprintf(query, "select * from(\
f.scheduled_departure as departure,\
f.scheduled_arrival as arrival,\
0 as num_conection,\
count(s.seat_no) - count(b.ticket_no) as num_seats_available\
from\
flights f\
join\
aircrafts_data a on f.aircraft_code = a.aircraft_code\
join\
seats s on a.aircraft_code = s.aircraft_code\
left join\
boarding_passes b on f.flight_id=b.flight_id and s.seat_no = b.seat_no\
where\
f.departure_airport='%s' and f.arrival_airport='%s'\
and f.scheduled_departure >= '%s'\
group by\
f.scheduled_departure, f.scheduled_arrival\
having\
count(s.seat_no) - count(b.ticket_no) > 0\
union\
select\
f1.scheduled_departure,\
f2.scheduled_arrival,\
1 as num_conection,\
least(count(s1.seat_no) - count(b1.ticket_no), count(s2.seat_no) - count(b2.ticket_no)) as num_seats_available\
from\
flights f1\
join\
flights f2 on f1.arrival_airport = f2.departure_airport\
join\
aircrafts_data a1 on f1.aircraft_code = a1.aircraft_code\
join\
aircrafts_data a2 on f2.aircraft_code = a2.aircraft_code\
join\
seats s1 on a1.aircraft_code = s1.aircraft_code\
join\
seats s2 on a2.aircraft_code = s2.aircraft_code\
left join\
boarding_passes b1 on f1.flight_id = b1.flight_id and s1.seat_no = b1.seat_no\
left join\
boarding_passes b2 on f2.flight_id = b2.flight_id and s2.seat_no = b2.seat_no\
where\
f1.departure_airport = '%s'\
and f2.arrival_airport = '%s'\
and f1.scheduled_departure >= '%s'\
and f1.scheduled_arrival <= f2.scheduled_departure\
and extract(epoch from (f2.scheduled_arrival - f1.scheduled_departure)) / 3600 < 24\
group by\
f1.scheduled_departure,\
f2.scheduled_arrival\
) as vuelos\
order by\
arrival-departure asc\
limit 10;", from, to, aux_date,from, to, aux_date);

  SQLExecDirect(stmt, (SQLCHAR *)query, SQL_NTS);

  SQLBindCol(stmt, 1, SQL_C_CHAR, y, sizeof(y), NULL);

  SQLBindCol(stmt, 2, SQL_C_CHAR, z, sizeof(z), NULL);

  SQLBindCol(stmt, 3, SQL_C_CHAR, v, sizeof(v), NULL);

  SQLBindCol(stmt, 4, SQL_C_CHAR, w, sizeof(w), NULL);

  i = 0;

  while (SQL_SUCCEEDED(ret = SQLFetch(stmt)))
  {
    snprintf(query_result_set[i], STRING_LEN, "%s %s %s %s", y, z, v, w);
    i++;
  }

  SQLCloseCursor(stmt);

  /* free up statement handle */
  SQLFreeHandle(SQL_HANDLE_STMT, stmt);

  /* DISCONNECT */
  ret = odbc_disconnect(env, dbc);
  if (!SQL_SUCCEEDED(ret))
  {
    return EXIT_FAILURE;
  }

  *n_choices = sizeof(query_result_set) / sizeof(query_result_set[0]);

  max_rows = MIN(*n_choices, max_rows);
  for (i = 0; i < max_rows; i++)
  {
    t = strlen(query_result_set[i]) + 1;
    t = MIN(t, max_length);
    strncpy((*choices)[i], query_result_set[i], t);
  }

  return EXIT_SUCCESS;
}

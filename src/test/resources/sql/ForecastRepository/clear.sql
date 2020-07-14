delete
from forecasts
where id != null;

alter sequence forecasts_seq restart with 1;
![Build](https://github.com/Illine/geomagnetic/workflows/beta/badge.svg) [![codecov](https://codecov.io/gh/Illine/geomagnetic/branch/develop/graph/badge.svg)](https://codecov.io/gh/Illine/geomagnetic)

# Geomagnetic
The service requests a geomagnetic forecast from [SWPC NOAA API](https://services.swpc.noaa.gov/text/3-day-geomag-forecast.txt "Geomagnetic Forecast") as a .txt file on a daily basis. After the file with forecast was receiving and parsing by the service, the result is stored to the database. All the process is entirely self-acting.  

This service is as part of microservices "Weather" for an android application [Weather: Any place on the Earth!](https://play.google.com/store/apps/details?id=net.c7j.wna&hl=ru "Google Play")
package pedrotti.gonzalo.proyecto.Clima;

class Main {
    public double temp;
    public double temp_min ;
    public double temp_max ;
    public double pressure;
    public double sea_level;
    public double grnd_level;
    public int humidity ;
    public double temp_kf;

    public Main(double temp, double temp_min, double temp_max, int humidity) {
        this.temp = temp;
        this.temp_min = temp_min;
        this.temp_max = temp_max;
        this.humidity = humidity;
    }

    public Main() {
    }

    public double getTemp() {
        return temp;
    }

    public void setTemp(double temp) {
        this.temp = temp;
    }

    public double getTemp_min() {
        return temp_min;
    }

    public void setTemp_min(double temp_min) {
        this.temp_min = temp_min;
    }

    public double getTemp_max() {
        return temp_max;
    }

    public void setTemp_max(double temp_max) {
        this.temp_max = temp_max;
    }

    public int getHumidity() {
        return humidity;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }
}

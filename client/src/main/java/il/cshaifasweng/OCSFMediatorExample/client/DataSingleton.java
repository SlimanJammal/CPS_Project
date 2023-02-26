package il.cshaifasweng.OCSFMediatorExample.client;

/*
*  since this class is static we can use it to send data between scenes
*  to save data we first get an instance using : DataSingleton somename = DataSingleton.getInstance();
*  then we save what ever data we want into the singleton using : somename.setData(yourData);
*  it takes Object data type so you can save whatever you want
*
*  to extract saved data : DataSingleton somename = DataSingleton.getInstance();
*  somename.getData();
*
* */


import java.io.Serializable;

public class DataSingleton implements Serializable {

    private static final DataSingleton instance = new DataSingleton();

    private Object data ;

    private String dataName;

    private String caller;


    private DataSingleton(){}

    public static DataSingleton getInstance(){
        return instance;
    }

    public Object getData(){
        return data;
    }

    public void setData(Object input){
        this.data = input;
    }

    public String getDataName() {
        return dataName;
    }

    public void setDataName(String dataName) {
        this.dataName = dataName;
    }

    public String getCaller() {
        return caller;
    }

    public void setCaller(String caller) {
        this.caller = caller;
    }
}

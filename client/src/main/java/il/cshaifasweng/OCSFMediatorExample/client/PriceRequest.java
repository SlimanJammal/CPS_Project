package il.cshaifasweng.OCSFMediatorExample.client;

public class PriceRequest {
    int number;
    String managerName;
    String Request;

    public PriceRequest(int number, String managerName, String request) {
        this.number = number;
        this.managerName = managerName;
        Request = request;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getManagerName() {
        return managerName;
    }

    public void setManagerName(String managerName) {
        this.managerName = managerName;
    }

    public String getRequest() {
        return Request;
    }

    public void setRequest(String request) {
        Request = request;
    }
}

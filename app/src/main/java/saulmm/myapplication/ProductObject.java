package saulmm.myapplication;

import java.util.UUID;

public class ProductObject {
    private String imagePath;

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public void setId(UUID id) {
        mId = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    private String name;
    private int pointSum;
    private int point;
    private UUID mId;
    public ProductObject(String name, String imagePath, int point, int pointSum) {
        this.mId= UUID.randomUUID();
        this.imagePath = imagePath;
        this.name = name;
        this.point = point;
        this.pointSum = pointSum;
    }
    public String getImagePath() {
        return imagePath;
    }
    public String getName() {
        return name;
    }
    public int getPoint(){return point;}
    public int getPointSum(){return pointSum;}
    public void setPointSum(int sum){pointSum=sum;}
    public UUID getId() {
        return mId;
    }
}
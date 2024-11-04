package model;

import javafx.scene.paint.Color;
import java.util.ArrayList;
import java.util.List;

public class Line {
    private Color color; // 线路的颜色
    private List<Station> stationList; // 线路中的站点列表
    private List<Train> trainList; // 线路上的火车列表
    private List<Position> path; // 路径上的位置列表，包含站点和中间点

    /**
     * 构造方法，初始化线路的起始站点和颜色，并设置中间点坐标。
     *
     * @param a 起始站点
     * @param b 终点站
     * @param c 线路颜色
     * @param middleX 中间点的X坐标
     * @param middleY 中间点的Y坐标
     */
    public Line(Station a, Station b, Color c, double middleX, double middleY) {
        stationList = new ArrayList<>();
        trainList = new ArrayList<>();
        path = new ArrayList<>();
        stationList.add(a); stationList.add(b);
        path.add(a.getPosition());  path.add(new Position(middleX,middleY)); path.add(b.getPosition());
        color = c;
        a.addLine(this); b.addLine(this);
    }

    /**
     * 获取线路的颜色。
     *
     * @return 线路颜色
     */
    public Color getColor() {
        return color;
    }

    /**
     * 获取线路的站点列表。
     *
     * @return 站点列表
     */
    public List<Station> getStationList() {
        return stationList;
    }

    /**
     * 获取线路的路径列表（包括站点和中间点的坐标）。
     *
     * @return 路径列表
     */
    public List<Position> getPath() {
        return path;
    }

    /**
     * 获取线路上的火车列表。
     *
     * @return 火车列表
     */
    public List<Train> getTrainList() {
        return trainList;
    }

    /**
     * 检查是否允许在指定的站点添加新的站点。
     *
     * @param s 要检查的站点
     * @return 若不在起点和终点之间，则返回 true 表示允许添加
     */
    public boolean addAllowed(Station s) {
        boolean allowed = stationList.indexOf(s) != -1
                &&  stationList.indexOf(s) != 0
                &&  stationList.indexOf(s) != stationList.size() - 1;
        return !allowed;
    }

    /**
     * 添加新的站点，并在路径中插入一个中间点。
     *
     * @param station 要添加的站点
     * @param middleX 中间点的X坐标
     * @param middleY 中间点的Y坐标
     */
    public void addStation(Station station, double middleX, double middleY) {
        path.add(new Position(middleX, middleY));
        path.add(station.getPosition());
        stationList.add(station);
    }

    /**
     * 在指定索引处插入一个站点和中间点，并更新火车的下一个目标索引。
     *
     * @param index 插入位置的索引
     * @param station 要插入的站点
     * @param middleX 中间点的X坐标
     * @param middleY 中间点的Y坐标
     */
    public void addStation(int index, Station station, double middleX, double middleY) {
        path.add(index, new Position(middleX, middleY));
        path.add(index, station.getPosition());
        stationList.add(index, station);

        station.addLine(this);

        // 更新所有火车的下一个目标点索引
        for (Train t : trainList) {
            t.setNextPointIndex(t.getNextPointIndex() + 2);
        }
    }

    /**
     * 在指定索引处添加站点，使用两个中间点，并更新火车的索引。
     *
     * @param index 插入位置的索引
     * @param station 要插入的站点
     * @param middleX 第一个中间点的X坐标
     * @param middleY 第一个中间点的Y坐标
     * @param middleX2 第二个中间点的X坐标
     * @param middleY2 第二个中间点的Y坐标
     */
    public void addStationFromLink(int index, Station station, double middleX, double middleY, double middleX2, double middleY2) {
        stationList.add(index, station);
        int indexToAdd = (index - 1) * 2 + 1;
        path.remove((index * 2) - 1);

        path.add(indexToAdd, new Position(middleX2, middleY2));
        path.add(indexToAdd, station.getPosition());
        path.add(indexToAdd, new Position(middleX, middleY));

        // 更新所有火车的下一个目标点索引
        for (Train t : trainList) {
            if (t.getNextPointIndex() > indexToAdd) {
                t.setNextPointIndex(t.getNextPointIndex() + 2);
                t.verif();
            }
        }
    }

    /**
     * 从线路中移除指定的站点，并更新火车索引。
     *
     * @param station 要移除的站点
     */
    public void removeStation(Station station) {
        if (isLoop()) {
            System.err.println("looped cannot choose which to remove");
            return;
        }
        station.removeLine(this);

        int index = stationList.indexOf(station);
        if (index == 0) {
            stationList.remove(station);
            path.remove(0);
            if (path.size() > 0)
                path.remove(0);
        } else {
            stationList.remove(station);
            path.remove(path.size() - 1);
            if (path.size() > 0)
                path.remove(path.size() - 1);
        }

        for (Train t : trainList) {
            if (index == 0) {
                t.setNextPointIndex(t.getNextPointIndex() - 2);
                System.err.println("SUB 2 INDEX");
            }

            if (t.getLine() != null)
                t.verif();
        }
    }

    /**
     * 在回路模式下移除第一个或最后一个站点。
     *
     * @param loop 回路的站点
     * @param first 是否移除第一个站点
     */
    public void removeLoop(Station loop, boolean first) {
        if (first) {
            stationList.remove(0);
            path.remove(0);
            if (path.size() > 0)
                path.remove(0);
        } else {
            stationList.remove(stationList.size() - 1);
            path.remove(path.size() - 1);
            if (path.size() > 0)
                path.remove(path.size() - 1);
        }
    }

    /**
     * 添加火车到线路上，并关联线路。
     *
     * @param train 要添加的火车
     */
    public void addTrain(Train train) {
        trainList.add(train);
        train.setLine(this);
    }

    /**
     * 从线路上移除火车。
     *
     * @param train 要移除的火车
     */
    public void removeTrain(Train train) {
        trainList.remove(train);
    }

    /**
     * 检查线路上的站点是否包含指定形状的站点。
     *
     * @param s 要检查的站点形状类型
     * @return 若包含该形状的站点，返回 true
     */
    public boolean containsShape(ShapeType s) {
        for (Station st : stationList) {
            if (st.getType() == s)
                return true;
        }
        return false;
    }

    /**
     * 判断线路是否是回路结构，即起点和终点是否为同一站点。
     *
     * @return 若为回路结构，返回 true
     */
    public boolean isLoop() {
        return stationList.get(0) == stationList.get(stationList.size() - 1) && stationList.size() != 1;
    }

    /**
     * 返回线路的文本描述，包括所有站点的信息。
     *
     * @return 线路信息字符串
     */
    public String toString() {
        String s = "";
        for (int i = 0; i < stationList.size(); ++i)
            s += stationList.get(i) + "\n";
        return s;
    }
}

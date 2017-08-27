package cn.wingene.mallxm.purchase.tool;

import java.util.HashMap;
import java.util.Map;

import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;

import com.google.gson.annotations.SerializedName;

import junze.java.able.ICallBack;

import junze.androidxf.core.Agent;

import cn.wingene.mallxm.purchase.ask.AskRegionGetList;

/**
 * Created by Wingene on 2017/4/13.
 */

public class ZoneTool {

    static String[] titles = {"请选择省", "请选择市", "请选择区域"};

    public static void showExecutiveChoiseDialog(final Agent agent, final Zone zone, final ICallBack<Zone>
            iCallBack) {
        String title = null;
        String id = null;
        final Map<Integer, String> buttons = new HashMap<Integer, String>();
        buttons.put(DialogInterface.BUTTON_NEGATIVE, "取消");
        if (zone.getProvince() == null) {
            title = titles[0];
            id = "0";
        } else if (zone.getCity() == null) {
            title = titles[1];
            id = zone.getProvince().getId();
            buttons.put(DialogInterface.BUTTON_POSITIVE, "返回上一页");
        } else if (zone.getArea() == null) {
            title = titles[2];
            id = zone.getCity().getId();
            buttons.put(DialogInterface.BUTTON_POSITIVE, "返回上一页");
        }
        final String title_ = title;
        agent.ask(new AskRegionGetList.Request(id) {
            @Override
            public void updateUI(AskRegionGetList.Response rsp) {
                int size = rsp.getList().size();
                final String[] ids = new String[size];
                final String[] names = new String[size];
                for (int i = 0; i < rsp.getList().size(); i++) {
                    ids[i] = rsp.getList().get(i).getCode();
                    names[i] = rsp.getList().get(i).getName();
                }
                agent.showMenuDialog(title_, names, buttons, new OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                        case DialogInterface.BUTTON_NEGATIVE:
                            // do nothing;
                            break;
                        case DialogInterface.BUTTON_POSITIVE:
                            if (titles[0].equals(title_)) {
                                // do nothing;
                            } else if (titles[1].equals(title_)) {
                                zone.setProvince(null);
                            } else {
                                zone.setCity(null);
                            }
                            showExecutiveChoiseDialog(agent, zone, iCallBack);
                            break;
                        default:
                            IdName name = new IdName(ids[which], names[which]);
                            if (titles[0].equals(title_)) {
                                zone.setProvince(name);
                                showExecutiveChoiseDialog(agent, zone, iCallBack);
                            } else if (titles[1].equals(title_)) {
                                zone.setCity(name);
                                showExecutiveChoiseDialog(agent, zone, iCallBack);
                            } else {
                                zone.setArea(name);
                                iCallBack.callBack(zone);
                            }
                            break;
                        }

                    }
                });
            }
        });
    }

    public static class Zone {
        @SerializedName("Province")
        private IdName province;
        @SerializedName("City")
        private IdName city;
        @SerializedName("Area")
        private IdName area;

        public IdName getProvince() {
            return province;
        }

        public void setProvince(IdName province) {
            this.province = province;
        }

        public IdName getCity() {
            return city;
        }

        public void setCity(IdName city) {
            this.city = city;
        }

        public IdName getArea() {
            return area;
        }

        public void setArea(IdName area) {
            this.area = area;
        }

        public String getAllName() {
            String[] names = new String[3];
            names[0] = province != null ? province.getName() : "";
            names[1] = city != null ? city.getName() : "";
            names[2] = area != null ? area.getName() : "";
            return new StringBuilder().append(names[0]).append(names[1]).append(names[2]).toString();
        }

        public String getAllCode(){
            String[] ids = new String[3];
            ids[0] = province != null ? province.getId() : "";
            ids[1] = city != null ? city.getId() : "";
            ids[2] = area != null ? area.getId() : "";
            return new StringBuilder().append(ids[0]).append(ids[1]).append(ids[2]).toString();
        }

    }

    public static class IdName {
        String id;
        String name;

        public IdName(String id, String name) {
            this.id = id;
            this.name = name;
        }

        public String getId() {
            return id;
        }

        public String getName() {
            return name;
        }
    }
}

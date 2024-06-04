package com.xmzy.bank.ghb;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.io.IOUtils;

import com.xmzy.base.util.SnowFlakeUtil;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author fronttang
 * @date 2021/10/20
 */
public class InvoiceFileTester {

    private static List<CustData> custData = new ArrayList<InvoiceFileTester.CustData>() {
        /**
         *
         */
        private static final long serialVersionUID = 1L;

        {
            add(new CustData("1449913478135517184", "京CAAN01", "5280006960013"));
            add(new CustData("1446351701243924480", "京CAAN02", "5280006760002"));
            add(new CustData("1449914465638256640", "京CAAN03", "5280006960009"));
            add(new CustData("1449915320575823872", "京CAAN04", "5280006960008"));
            add(new CustData("1449915524704210944", "京CAAN05", "5280006960007"));
            add(new CustData("1449915653704224768", "京CAAN06", "5280006960006"));
            add(new CustData("1449916285592899584", "京CAAN07", "5280006960005"));
            add(new CustData("1449916419156316160", "京CAAN08", "5280006960003"));
            add(new CustData("1449916620231249920", "京CAAN09", "5280006960002"));
            add(new CustData("1449916754528669696", "京CAAN10", "5280006960001"));
            add(new CustData("1449915524704210944", "京CAAN51", "5280006960007"));
            add(new CustData("1449915524704210944", "京CAAN52", "5280006960007"));
            // add(new CustData("1446351701243924480", "京CAAN11", "5280006760002"));
        }
    };

    private static List<String> stationName = new ArrayList<String>() {
        /**
         *
         */
        private static final long serialVersionUID = 1L;

        {
            add("深圳市福田站");
            add("深圳市南山站");
            add("深圳市罗湖站");
            add("深圳市盐田站");
            add("深圳市宝安站");
            add("深圳市龙岗站");
            add("深圳市龙华站");
            add("深圳市大鹏站");
            add("深圳市松岗站");
            add("深圳市坪山站");
            add("深圳市光明站");
        }
    };

    private static Long startSerNo = 49000000L;

    public static void main(String[] args) throws FileNotFoundException, IOException {

        List<InvoiceData> invoiceDatas = new ArrayList<InvoiceFileTester.InvoiceData>();

        // 随机生成数据
        custData.forEach((cust) -> {

            for (int i = 0; i < 2; i++) {
                double income = RandomUtil.randomDouble(100, 2000, 2, RoundingMode.HALF_UP);

                InvoiceData data = new InvoiceData();
                data.setSerNo(Convert.toStr(startSerNo++));
                data.setGenTime(DateUtil.format(new Date(), "yyyyMMdd'T'HHmmss"));
                data.setAccount(cust.getAccount());
                data.setIncome(Convert.toStr(income));
                data.setDetailNo(SnowFlakeUtil.uniqueString());
                data.setCardCode(cust.getCardNo());
                data.setEnTime(DateUtil.format(new Date(), "yyyyMMdd'T'HHmmss"));
                data.setEnStationName(stationName.get(RandomUtil.randomInt(0, 10)));
                data.setExTime(DateUtil.format(new Date(), "yyyyMMdd'T'HHmmss"));
                data.setExStationName(stationName.get(RandomUtil.randomInt(0, 10)));
                data.setVehicleId(cust.getCarNo());

                invoiceDatas.add(data);
            }
        });

        LinkedList<String> collector = new LinkedList<String>();

        invoiceDatas.forEach((data) -> {
            LinkedList<String> dataStr = new LinkedList<String>();

            dataStr.add(data.getSerNo());
            dataStr.add(data.getGenTime());
            dataStr.add(data.getAccount());
            dataStr.add(data.getBAccount());
            dataStr.add(data.getAccType());
            dataStr.add(data.getIncome());
            dataStr.add(data.getDetailNo());
            dataStr.add(data.getCardCode());
            dataStr.add(data.getEnTime());
            dataStr.add(data.getEnStationName());
            dataStr.add(data.getExTime());
            dataStr.add(data.getExStationName());
            dataStr.add(data.getInfoType());
            dataStr.add(data.getServiceType());
            dataStr.add(data.getVehicleId());
            dataStr.add(data.getUserNo());
            dataStr.add(data.getDestCode());
            dataStr.add(data.getBaccBrno());
            dataStr.add(data.getAgentAcno());
            dataStr.add(data.getAgentBrno());
            dataStr.add(data.getOrgQsBrno());
            dataStr.add(data.getOrgAcName());
            dataStr.add(data.getAgreeNo());

            String content = StrUtil.join("|", dataStr);
            collector.add(content);

            System.out.println(content);
        });

        String fileName = StrUtil.format("{}{}{}", "GHB_518510003_INVOICE_",
            DateUtil.format(new Date(), DatePattern.PURE_DATE_FORMAT), ".txt");

        IOUtils.writeLines(collector, null, new FileOutputStream(new File("C:/Users/tangf/Desktop/" + fileName)),
            CharsetUtil.UTF_8);
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    private static class CustData {

        /**
         * 银行账号
         */
        private String account;

        /**
         * 车牌号
         */
        private String carNo;

        /**
         * 卡号
         */
        private String cardNo;
    }

    @Data
    private static class InvoiceData {

        /**
         * 流水号
         */
        private String serNo = "";

        /**
         * 生成日期，YYYYMMDDThhmmss格式，例如20210214T085151
         */
        private String genTime = "";

        /**
         * 记账卡客户银行账号
         */
        private String account = "";

        /**
         * 备付金账号
         */
        private String bAccount = "";

        /**
         * 记账卡客户账户类型 0-对公 1-储蓄 2-信用卡 3-跨行 免保
         */
        private String accType = "0";

        /**
         * 转账金额
         */
        private String income = "";

        /**
         * 车道序列号
         */
        private String detailNo = "";

        /**
         * 卡号
         */
        private String cardCode = "";

        /**
         * 入口日期，YYYYMMDDThhmmss 格式，例如20210214T085151
         */
        private String enTime = "";

        /**
         * 入口收费站名
         */
        private String enStationName = "";

        /**
         * 出口日期，YYYYMMDDThhmmss格式，例如20210214T085151
         */
        private String exTime = "";

        /**
         * 出口收费站名
         */
        private String exStationName = "";

        /**
         * 具体交易场景 1、封闭式高速公路 2、开放式高速公路 3、停车场 4、加油站 5、服务区 6、市政服务
         */
        private String infoType = "1";

        /**
         * 交易场景类型 1、高速公路通行交 易 2、拓展应用交
         */
        private String serviceType = "1";

        /**
         * 车牌脱敏信息文字描述
         */
        private String vehicleId = "";

        /**
         * 用户编号
         */
        private String userNo = "518510003";

        /**
         * 组织机构代码
         */
        private String destCode = "MA091PGB-0";

        /**
         * 备付金账户开户行号
         */
        private String baccBrno = "";

        /**
         * 代理行内部账号
         */
        private String agentAcno = "";

        /**
         * 代理行内部账号开户行号
         */
        private String agentBrno = "";

        /**
         * 记账卡客户账号开户行号
         */
        private String orgQsBrno = "";

        /**
         * 记账卡客户账号名称
         */
        private String orgAcName = "华兴银行";

        /**
         * 跨行划账合同协议号
         */
        private String agreeNo = "";
    }
}

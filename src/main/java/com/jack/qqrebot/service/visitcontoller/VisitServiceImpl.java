package com.jack.qqrebot.service.visitcontoller;

import ch.qos.logback.core.util.ExecutorServiceUtil;
import com.jack.qqrebot.enumm.ConnType;
import org.springframework.stereotype.Service;

import javax.xml.crypto.Data;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Auther: mujj
 * @Date: 2019/5/10 23:41
 * @Description:
 * @Version: 1.0
 */
@Service("visitService")
public class VisitServiceImpl implements VisitService {

    private static volatile ConcurrentHashMap<String,List<Long>> concurrentHashMap = new ConcurrentHashMap<>();

    private static volatile ConcurrentHashMap<String,List<Long>> countMap = new ConcurrentHashMap<>();

    private static volatile ConcurrentHashMap<String,Boolean> flag = new ConcurrentHashMap<>();

    @Override
    public ConnType addVisitRecord(String userId, long timestamp) {
        if(flag.get(userId) != null && flag.get(userId)){
            if(countMap.get(userId) != null){
                if(countMap.get(userId).size() == 1){
                    flag.put(userId,false);
                    return ConnType.IS_WARN;
                }
                if(countMap.get(userId).size() == 2||countMap.get(userId).size() == 3 ||countMap.get(userId).size() == 4){
                    flag.put(userId,false);
                    return ConnType.IS_JINYAN;
                }
                if(countMap.get(userId).size() == 5){
                    return ConnType.IS_HMD;
                }
            }
        }
        return addRecord(userId);
    }


    private ConnType addRecord(String userId){
        List<Long> list =  concurrentHashMap.get(userId) == null? new ArrayList<>():concurrentHashMap.get(userId);
        list.add(new Date().getTime());
        concurrentHashMap.put(userId,list);
        if(list.size() >= 5){
            List<Long> list1 = countMap.get(userId) == null ? new ArrayList<Long>() : countMap.get(userId);
            list1.add(new Date().getTime());
            countMap.put(userId,list1);
            for (int i = 0; i < 5; i++) {
                list.remove(0);
            }
            concurrentHashMap.put(userId,list);
            flag.put(userId,true);
        }
        return ConnType.IS_CAN;
    }

    @Override
    public void check(){
        if(concurrentHashMap != null && concurrentHashMap.size() > 0){
            long time1 = new Date().getTime();
            concurrentHashMap.forEach((s, list) -> {
                Iterator<Long> iterator = list.iterator();
                while (iterator.hasNext()){
                    Long next = iterator.next();
                    if (time1 - next > 60){
                        iterator.remove();
                    }
                }
            });
            concurrentHashMap.forEach((s,list) -> {
                if(list.size() >5){
                    List<Long> list1 = countMap.get(s) == null ? new ArrayList<Long>() : countMap.get(s);
                    list1.add(new Date().getTime());
                    countMap.put(s,list1);
                }
            });
        }

    }

    @Override
    public void checkHMD() {
        if(countMap != null && countMap.size() > 0){
            long time1 = new Date().getTime();
            countMap.forEach((s, list) -> {
                list.removeIf(x -> time1 - x > 3*60*60);
                if(list.size() < 5){
                    flag.put(s,false);
                }
            });
        }
    }

}

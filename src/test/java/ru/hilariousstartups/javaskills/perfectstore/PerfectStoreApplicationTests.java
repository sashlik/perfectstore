package ru.hilariousstartups.javaskills.perfectstore;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.hilariousstartups.javaskills.perfectstore.endpoint.PerfectStoreEndpoint;
import ru.hilariousstartups.javaskills.perfectstore.model.vo.*;
import ru.hilariousstartups.javaskills.perfectstore.service.PerfectStoreService;
import ru.hilariousstartups.javaskills.perfectstore.service.WorldContext;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@Slf4j
class PerfectStoreApplicationTests {

    @Autowired
    private PerfectStoreService perfectStoreService;

    @Autowired
    private PerfectStoreEndpoint endpoint;

    @Autowired
    private WorldContext worldContext;

    private int cnt = 0;

    @Test
    void contextLoads() {
        CurrentWorldResponse world;
        do {
             world = endpoint.tick(new CurrentTickRequest());
             if (world.getCurrentTick() % 100 == 0) {
                 log.info("Текущие затраты:" + world.getSalaryCosts());
             }

            if (cnt++ == 300) {
                List<HireEmployeeCommand> hireEmployeeCommands = new ArrayList<>();
                HireEmployeeCommand hireCmd = new HireEmployeeCommand();
                hireCmd.setCheckoutLineId(5);
                hireCmd.setExperience(EmployeeExperience.senior);

                hireEmployeeCommands.add(hireCmd);
                CurrentTickRequest request = new CurrentTickRequest();
                request.setHireEmployeeCommands(hireEmployeeCommands);
                endpoint.tick(request);
            }

            /* if (cnt++ == 300) {
                 List<FireEmployeeCommand> fireEmployeeCommands = new ArrayList<>();
                 FireEmployeeCommand fireCmd = new FireEmployeeCommand();
                 fireCmd.setEmployeeId(1);
                 fireEmployeeCommands.add(fireCmd);

                 fireCmd = new FireEmployeeCommand();
                 fireCmd.setEmployeeId(2);
                 fireEmployeeCommands.add(fireCmd);

                 fireCmd = new FireEmployeeCommand();
                 fireCmd.setEmployeeId(3);
                 fireEmployeeCommands.add(fireCmd);

                 fireCmd = new FireEmployeeCommand();
                 fireCmd.setEmployeeId(4);
                 fireEmployeeCommands.add(fireCmd);

                 fireCmd = new FireEmployeeCommand();
                 fireCmd.setEmployeeId(5);
                 fireEmployeeCommands.add(fireCmd);
fireCmd = new FireEmployeeCommand();
                 fireCmd.setEmployeeId(6);
                 fireEmployeeCommands.add(fireCmd);
fireCmd = new FireEmployeeCommand();
                 fireCmd.setEmployeeId(7);
                 fireEmployeeCommands.add(fireCmd);
fireCmd = new FireEmployeeCommand();
                 fireCmd.setEmployeeId(8);
                 fireEmployeeCommands.add(fireCmd);
fireCmd = new FireEmployeeCommand();
                 fireCmd.setEmployeeId(9);
                 fireEmployeeCommands.add(fireCmd);
fireCmd = new FireEmployeeCommand();
                 fireCmd.setEmployeeId(10);
                 fireEmployeeCommands.add(fireCmd);
fireCmd = new FireEmployeeCommand();
                 fireCmd.setEmployeeId(11);
                 fireEmployeeCommands.add(fireCmd);

                 CurrentTickRequest request = new CurrentTickRequest();
                 request.setFireEmployeeCommands(fireEmployeeCommands);
                 endpoint.tick(request);
             }*/
        }
        while (!world.getGameOver());


    }

}

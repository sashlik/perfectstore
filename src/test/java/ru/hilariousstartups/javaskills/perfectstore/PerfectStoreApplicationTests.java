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

//@SpringBootTest
@Slf4j
class PerfectStoreApplicationTests {

    @Autowired
    private PerfectStoreService perfectStoreService;

    @Autowired
    private PerfectStoreEndpoint endpoint;

    @Autowired
    private WorldContext worldContext;

    private int cnt = 0;

  //  @Test
    void contextLoads() {
        CurrentWorldResponse world;
        do {
             world = endpoint.tick(new CurrentTickRequest());
            /*if (world.getCurrentTick() == 10) {
                System.out.println("Снимаем с кассы!");
                SetOffCheckoutLineCommand setOffCheckoutLineCommand = new SetOffCheckoutLineCommand();
                setOffCheckoutLineCommand.setEmployeeId(1);
                CurrentTickRequest request = new CurrentTickRequest();
                request.setSetOffCheckoutLineCommands(List.of(setOffCheckoutLineCommand));
                endpoint.tick(request);
            }*/
            if (world.getCurrentTick() == 10) {
                System.out.println("Снимаем с кассы!");
                FireEmployeeCommand command = new FireEmployeeCommand();
                command.setEmployeeId(1);
                CurrentTickRequest request = new CurrentTickRequest();
                request.setFireEmployeeCommands(List.of(command));
                endpoint.tick(request);
            }
            if (world.getCurrentTick() == 1500) {
                SetOnCheckoutLineCommand command = new SetOnCheckoutLineCommand();
                command.setEmployeeId(1);
                command.setCheckoutLineId(2);
                CurrentTickRequest request = new CurrentTickRequest();
                request.setSetOnCheckoutLineCommands(List.of(command));
                endpoint.tick(request);
            }
            //if (world.getCurrentTick())
           /*  if (world.getCurrentTick() % 100 == 0) {
                 log.info("Текущие затраты:" + world.getSalaryCosts());
             }*/


        }
        while (!world.getGameOver());


    }

}

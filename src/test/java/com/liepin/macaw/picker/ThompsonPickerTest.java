package com.liepin.macaw.picker;

import com.google.common.collect.Lists;
import com.liepin.macaw.exception.ConfigException;
import com.liepin.macaw.model.EstimateParamSet;
import com.liepin.macaw.model.Policy;
import com.liepin.macaw.utils.ModelUtils;
import com.liepin.macaw.utils.PolicyCounter;
import org.apache.log4j.Logger;
import org.junit.Test;

import java.util.List;

public class ThompsonPickerTest {
    private static final Logger logger = Logger.getLogger(ThompsonPickerTest.class);

    @Test
    public void testPicker(){
        List<Policy> policies = Lists.newArrayList();
        EstimateParamSet params = new EstimateParamSet();
        policies.add(ModelUtils.newPolicy("P1", 0, "Policy1", false));
        params.putParam(ModelUtils.newParam("P1", 20, 100));
        policies.add(ModelUtils.newPolicy("P2", 0, "Policy2", false));
        params.putParam(ModelUtils.newParam("P2", 200, 1000));
        policies.add(ModelUtils.newPolicy("P3", 0, "Policy3", false));
        params.putParam(ModelUtils.newParam("P3", 15, 100));
        policies.add(ModelUtils.newPolicy("P4", 0, "Policy4", false));
        params.putParam(ModelUtils.newParam("P4", 12, 100));
        try {
            ThompsonPicker picker = DataPickers.newThompsonPicker(policies, params);
            PolicyCounter counter = new PolicyCounter();
            for (int i = 0; i < 1000; i++) {
                counter.addPolicy(picker.pick(String.valueOf(i)));
            }
            counter.print();
            assert counter.getPolicyCount("P1") > counter.getPolicyCount("P2");
            assert counter.getPolicyCount("P2") > counter.getPolicyCount("P3");
            assert counter.getPolicyCount("P3") > counter.getPolicyCount("P4");
        } catch (ConfigException e) {
            e.printStackTrace();
        }
    }
}

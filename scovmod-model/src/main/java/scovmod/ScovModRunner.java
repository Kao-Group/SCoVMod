package scovmod;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import scovmod.model.Model;
import scovmod.model.input.config.ConfigParameters;
import scovmod.model.input.config.InputData;
import scovmod.model.input.config.Parameters;
import scovmod.model.output.IOutputModule;
import scovmod.model.output.IResult;
import scovmod.model.untested.Factory;

public class ScovModRunner {
    private static Logger log = LoggerFactory.getLogger(Factory.class);

    public static IResult apply(
            InputData data,
            ConfigParameters config,
            Parameters params,
            IOutputModule out
    ) {
        Model model = Factory.buildModel(data, config, params, out);

        log.info("Starting run '{}' with {}", model.getModelId(), params);

        long startTime = System.currentTimeMillis();
        IResult result = model.run();
        long endTime = System.currentTimeMillis();

        log.info("Run '{}' took {} seconds", model.getModelId(), (endTime - startTime) / 1000);

        return result;
    }
}

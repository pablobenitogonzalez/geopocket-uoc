package edu.uoc.geopocket.statistics.factory;

import edu.uoc.geopocket.berock.services.BerockServiceImpl;
import edu.uoc.geopocket.common.Tool;
import edu.uoc.geopocket.common.exceptions.GeoPocketException;
import edu.uoc.geopocket.common.services.StatisticsService;
import edu.uoc.geopocket.liquec.services.LiquecServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Component
public class ToolFactory {

    private static final Map<Tool, Class<? extends StatisticsService>> TOOL_FACTORY = new HashMap<>();
    static {
        TOOL_FACTORY.put(Tool.BEROCK, BerockServiceImpl.class);
        TOOL_FACTORY.put(Tool.LIQUEC, LiquecServiceImpl.class);
    }

    private final ApplicationContext context;

    @Autowired
    public ToolFactory(ApplicationContext context) {
        this.context = context;
    }

    public StatisticsService getToolService(final Tool tool) {
        Class<? extends StatisticsService> toolService = Optional.ofNullable(TOOL_FACTORY.get(tool))
                .orElseThrow(() -> new GeoPocketException("Tool not found!"));
        return context.getBean(toolService);
    }


}

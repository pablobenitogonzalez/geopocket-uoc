package edu.uoc.geopocket.statistics.factory;

import edu.uoc.geopocket.common.exceptions.GeoPocketException;
import edu.uoc.geopocket.common.services.ToolService;
import edu.uoc.geopocket.liquec.services.LiquecServiceImpl;
import edu.uoc.geopocket.statistics.common.Tool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Component
public class ToolFactory {

    private static Map<Tool, Class<? extends ToolService>> TOOL_FACTORY = new HashMap<>();
    static {
        TOOL_FACTORY.put(Tool.LIQUEC, LiquecServiceImpl.class);
    }

    private ApplicationContext context;

    @Autowired
    public ToolFactory(ApplicationContext context) {
        this.context = context;
    }

    public ToolService getToolService(final Tool tool) {
        Class<? extends ToolService> toolService = Optional.ofNullable(TOOL_FACTORY.get(tool))
                .orElseThrow(() -> new GeoPocketException("Tool not found!"));
        return context.getBean(toolService);
    }


}

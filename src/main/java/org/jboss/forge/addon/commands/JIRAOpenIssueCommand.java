package org.jboss.forge.addon.commands;

import java.awt.Desktop;
import java.net.URI;

import javax.inject.Inject;

import org.jboss.forge.addon.configuration.Configuration;
import org.jboss.forge.addon.ui.command.AbstractUICommand;
import org.jboss.forge.addon.ui.context.UIBuilder;
import org.jboss.forge.addon.ui.context.UIContext;
import org.jboss.forge.addon.ui.context.UIExecutionContext;
import org.jboss.forge.addon.ui.input.UIInputMany;
import org.jboss.forge.addon.ui.metadata.UICommandMetadata;
import org.jboss.forge.addon.ui.metadata.WithAttributes;
import org.jboss.forge.addon.ui.output.UIOutput;
import org.jboss.forge.addon.ui.result.Result;
import org.jboss.forge.addon.ui.result.Results;
import org.jboss.forge.addon.ui.util.Categories;
import org.jboss.forge.addon.ui.util.Metadata;

public class JIRAOpenIssueCommand extends AbstractUICommand
{
   @Inject
   @WithAttributes(label = "JIRA issues")
   private UIInputMany<String> arguments;

   @Inject
   private Configuration config;

   @Override
   public UICommandMetadata getMetadata(UIContext context)
   {
      return Metadata.forCommand(getClass())
               .name("JIRA: Open Issue")
               .description("Opens an issue or a group of issues")
               .category(Categories.create("JIRA"));
   }

   @Override
   public void initializeUI(UIBuilder builder) throws Exception
   {
      builder.add(arguments);
   }

   @Override
   public Result execute(UIExecutionContext context) throws Exception
   {
      UIOutput out = context.getUIContext().getProvider().getOutput();
      String jiraURL = getJIRAUrl();
      Desktop desktop = Desktop.getDesktop();
      if (arguments.hasValue())
      {
         for (String issue : arguments.getValue())
         {
            out.out().println("Opening issue " + issue);
            desktop.browse(new URI(jiraURL + "/browse/" + issue));
         }
      }
      else
      {
         out.out().println("Opening new issue");
         desktop.browse(new URI(jiraURL + "/secure/CreateIssue!default.jspa"));
      }
      return Results.success();
   }

   @Override
   public boolean isEnabled(UIContext context)
   {
      return getJIRAUrl() != null;
   }

   private String getJIRAUrl()
   {
      Configuration subset = config.subset("jira");
      return subset.getString("url");
   }
}

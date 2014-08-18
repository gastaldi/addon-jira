package org.jboss.forge.addon.commands;

import org.jboss.forge.addon.configuration.Configuration;
import org.jboss.forge.addon.resource.URLResource;
import org.jboss.forge.addon.ui.command.AbstractUICommand;
import org.jboss.forge.addon.ui.context.UIBuilder;
import org.jboss.forge.addon.ui.context.UIContext;
import org.jboss.forge.addon.ui.context.UIExecutionContext;
import org.jboss.forge.addon.ui.input.UIInput;
import org.jboss.forge.addon.ui.metadata.UICommandMetadata;
import org.jboss.forge.addon.ui.metadata.WithAttributes;
import org.jboss.forge.addon.ui.util.Metadata;
import org.jboss.forge.addon.ui.util.Categories;
import org.jboss.forge.addon.ui.result.Result;
import org.jboss.forge.addon.ui.result.Results;

import java.lang.Override;
import java.lang.Exception;

import javax.inject.Inject;

public class JIRASetupCommand extends AbstractUICommand
{

   @Inject
   @WithAttributes(label = "JIRA URL", required = true)
   private UIInput<URLResource> url;

   @Inject
   private Configuration config;

   @Override
   public UICommandMetadata getMetadata(UIContext context)
   {
      return Metadata.forCommand(getClass())
               .name("JIRA: Setup")
               .description("Setup the JIRA Addon")
               .category(Categories.create("JIRA", "Setup"));
   }

   @Override
   public void initializeUI(UIBuilder builder) throws Exception
   {
      builder.add(url);
   }

   @Override
   public Result execute(UIExecutionContext context)
   {
      String targetUrl = url.getValue().getFullyQualifiedName();
      Configuration subset = config.subset("jira");
      subset.setProperty("url", targetUrl);
      return Results.success("JIRA URL set to: "+targetUrl);
   }
}

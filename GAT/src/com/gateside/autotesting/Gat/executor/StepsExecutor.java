package com.gateside.autotesting.Gat.executor;

import com.gateside.autotesting.Gat.executor.CaseExecutor;
import com.gateside.autotesting.Gat.dataobject.InvokedMethodInfo;
import com.gateside.autotesting.Gat.dataobject.testcase.StepsCase;
import com.gateside.autotesting.Gat.dataobject.testcase.TestStep;
import com.gateside.autotesting.Gat.util.GlobalConfig;
import com.gateside.autotesting.Gat.util.StepValuePool;
import com.gateside.autotesting.Lib.common.SimpleLogger;
import com.gateside.autotesting.Lib.httpunitService.HttpUnitHelper;


public class StepsExecutor extends CaseExecutor
{
	protected String caseFilePath="";	
    protected StepsCase targetCase=null;

	public StepsExecutor(String caseID,String caseFilePath)
	{
		this.caseFilePath=GlobalConfig.getAutoProjectName()+"DataFiles"+GlobalConfig.getSlash()+"Xmls"+GlobalConfig.getSlash()+caseFilePath;
		this.setCaseID(caseID);
	}
	

	@Override
	public void setUp() throws Exception
    {
		 preCleanup();
		 setGlobalConfig();
		 exectuePreStep();
	}
	
	@Override
	public void executeCase() throws Exception 
	{
		throw new Exception("function not impliment!");
	}

	@Override
	public void tearDown() throws Exception 
	{
		try 
		{
			GlobalConfig.setStepsParameterFilePath(GlobalConfig.getAutoProjectName()+"DataFiles"+GlobalConfig.getSlash()+"Xmls"+GlobalConfig.getSlash()+"TearDownParameters.xml"); //set glocal config for pre step parameters
			if(targetCase.TearDownType!=null)
			{
				SimpleLogger.logInfo(this.getClass(),"tearDown: "+targetCase.TearDownType+targetCase.TearDown);
				switch (targetCase.TearDownType.ordinal())
				{
				case 0:
					executeSql(targetCase.TearDown);
					break;
				case 1:
					invokeDBStep(targetCase.TearDown);
				case 2:
					InvokedMethodInfo methodInfo= parserPreStepMethodInfo(targetCase.TearDown,null);
					invokeMethod(methodInfo);
				}
			}
		} catch (Exception e) 
		{
		  SimpleLogger.logError(this.getClass(),e);
		}
		finally
		{
			HttpUnitHelper.cleanConversation();
			StepValuePool.cleanValuePool();
		}
		
	}

	protected  void setGlobalConfig()
	{
		SimpleLogger.logInfo(this.getClass(),"setGlobalConfig: set testcase file path as "+this.caseFilePath);
		GlobalConfig.setTestCaseFilePath(this.caseFilePath); //set global config for testcase file path	
	}
	
	protected StepsCase getTestCase() throws Exception
	{
		throw new Exception("The function doesn't implient.");
	}
	
	protected void exectuePreStep() throws Exception
	{
		SimpleLogger.logInfo(this.getClass(),"executePreStep:");
		targetCase=getTestCase();
		GlobalConfig.setStepsParameterFilePath(GlobalConfig.getAutoProjectName()+"DataFiles"+GlobalConfig.getSlash()+"Xmls"+GlobalConfig.getSlash()+"SetupParameters.xml"); //set glocal config for pre step parameters
		if(targetCase.SetupType!=null)
		{
			SimpleLogger.logInfo(this.getClass(),"Execute setup method: "+targetCase.SetupType);
			switch (targetCase.SetupType.ordinal())
			{
			case 0:
				executeSql(targetCase.Setup);
				break;
			case 1:
				invokeDBStep(targetCase.Setup);
			case 2:
				InvokedMethodInfo methodInfo= parserPreStepMethodInfo(targetCase.Setup,null);
				Object returnValue=invokeMethod(methodInfo);
				StepValuePool.createInstance().getValueDic().put(GlobalConfig.getPreStepResult(),returnValue);
			}
		}
		
	}

    protected InvokedMethodInfo getStepMethodInfo(TestStep step)
    {
    	InvokedMethodInfo resultInfo=new InvokedMethodInfo();
    	resultInfo.classFullName=step.StepAssembly+step.StepGroup;
    	resultInfo.methodName=step.StepName;
    	resultInfo.parameters.add(step.StepParameterID);
    	resultInfo.jarFilePath=GlobalConfig.getStepMethodJarPath();
    	return resultInfo;
    }

}

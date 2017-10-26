package com.workflow.engine.service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;

import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.InvalidRemoteException;
import org.eclipse.jgit.api.errors.TransportException;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CloneProject {
	@Value("${build: default value something}")
	private String build;
	
	@Value("${test}")
	private String test;
	
	@Value("${run}")
	private String run;
	
	@Value("${compile}")
	private String compile;

	
	private String project_url = "https://github.com/Shekharrajak/Trigger-Jenkins-Server"; 
	private String project_url1 = "https://github.com/Shekharrajak/PipelineExecution";

	@RequestMapping("/cloneFormGitCommand")
	public Object cloneItFromGitCommand() {
        StringBuffer output = new StringBuffer("the cloned output is : ") ; 
        try {
        	// git should be installed in system
        		String target = new String("git clone " + project_url);
        		//String target = new String("mkdir stackOver");
		        Runtime rt = Runtime.getRuntime();
		        Process proc = rt.exec(target);
		        proc.waitFor();

		        BufferedReader reader = new BufferedReader(new InputStreamReader(proc.getInputStream()));
		        String line = "";                       
		        while ((line = reader.readLine())!= null) {
		                output.append(line + "\n");
		        }
//		        System.out.println("### " + output);
		        return output;
		} catch (Throwable t) {
		        t.printStackTrace();
		}
		return output;

	}
	
	/*
	 * helpful link : http://www.codeaffine.com/2015/11/30/jgit-clone-repository/
	 * */
	private File cloned_repo_path = new File("./cloned_repo"); 
	@RequestMapping("/clone")
	public Object cloneIt() throws IOException, InvalidRemoteException, TransportException, GitAPIException {
        //StringBuffer output = new StringBuffer("the cloned output is : ") ; 
        /*
     // Create a new repository; the path must exist
        Repository newlyCreatedRepo = FileRepositoryBuilder.create(
            new File("/tmp/new_repo/.git"));

        // Open an existing repository
        Repository existingRepo = new FileRepositoryBuilder()
            .setGitDir(new File("my_repo/.git"))
            .build();
            */
		
		// remove the present /cloned_repo folder
		if (cloned_repo_path.exists()) {
			runUnixCommand("rm -rf " + cloned_repo_path);
		}
		
		
        Git git = Git.cloneRepository()
        		  .setURI( project_url1 )
        		  .setDirectory( cloned_repo_path )
        		  .setCloneAllBranches( true )
        		  .call();
//        Git git_open = Git.open( new F‌ile( "/path/to/repo/.git" ) );

        return git;

	}
	
	private File jenkinsfile_path = new File("./jenkinsFolder/Jenkinsfile"); 
	@RequestMapping("/generateJenkinsfile")
	public Object generateJenkinsFile() throws IOException {
		createfile(jenkinsfile_path);
		BufferedWriter writer = null;
		FileWriter fw = null;

			try {
	
				fw = new FileWriter(jenkinsfile_path);
				writer = new BufferedWriter(fw);
				 StringBuilder sb = new StringBuilder();
		          writer.append("pipeline {\n");
		          writer.append("        agent { docker any }\n");
		          writer.append("        stages {\n");
		          writer.append("            steps {\n");


		              if(build != null)
		              {
		                  writer.append("              sh '"+ build +"' \n");
		              }
		              if(compile != null)
		              {
		                  writer.append("              sh '"+ compile +"' \n");
		              }
		              
		              if(test != null)
		              {
		                  writer.append("              sh '"+ test +"' \n");
		              }
		              
		              if(run != null)
		              {
		                  writer.append("              sh '"+ run +"' \n");
		              }
		          writer.append("                       }\n");
		          writer.append("               }\n");
		          writer.append("         }\n");
		    	
				System.out.println("Done");
	
			} catch (IOException e) {
	
//				e.printStackTrace();
				System.out.println("Unable to generate files and folder.");
	
			} finally {
	
				try {
	
					if (writer != null)
						writer.close();
	
					if (fw != null)
						fw.close();
	
				} catch (IOException ex) {
	
					ex.printStackTrace();
	
				}
	
			}
			File Jenkinsfile_in_repo = new File("./cloned_repo/Jenkinsfile"); 
			copyJenkinsfileToRepo(Jenkinsfile_in_repo, jenkinsfile_path);
			return jenkinsfile_path;
	
	}
	
	
	public void createfile(File path) throws IOException {
		/* create the dir first */

		// if the directory does not exist, create it
		if (!path.exists()) {
		    System.out.println("creating directory: " + path.getName());
		    boolean result = false;

		    try{
				if (!path.getParentFile().exists()) {
					path.getParentFile().mkdirs();
				}
					
				if (!path.exists()) {
					path.createNewFile();
				}
				path.mkdir();
		        result = true;
		    } 
		    catch(SecurityException se){
		        //handle it
		    }        
		    if(result) {    
		        System.out.println("DIR created");  
		    }
		}
	}
	
	public void copyJenkinsfileToRepo(File JenkinsfileInRepo, File jenkinsfilePath) throws IOException {
		/*
		try {
			
		    FileUtils.copyFile(jenkinsfile_path, Jenkinsfile_in_repo);
		} catch (IOException e) {
		    e.printStackTrace();
		}
		*/
	    FileInputStream is = null;
	    OutputStream os = null;
	    try {
	        is = new FileInputStream(jenkinsfilePath);
	        os = new FileOutputStream(JenkinsfileInRepo);
	        byte[] buffer = new byte[1024];
	        int length;
	        while ((length = is.read(buffer)) > 0) {
	            os.write(buffer, 0, length);
	        }
	    } finally {
	        is.close();
	        os.close();
	    }
	}
	
	public boolean runUnixCommand(String cmd) {
        try {
//            String target = new String("./test.sh");
            //String target = new String("mkdir stackOver");
            Runtime rt = Runtime.getRuntime();
            Process proc = rt.exec(cmd);
            proc.waitFor();
            StringBuffer output = new StringBuffer();
            BufferedReader reader = new BufferedReader(new InputStreamReader(proc.getInputStream()));
            String line = "";                       
            while ((line = reader.readLine())!= null) {
                    output.append(line + "\n");
            }
            System.out.println("### " + output);
            return true;
	    } catch (Throwable t) {
	            t.printStackTrace();
	           return false;
	    }
	}
 
//	@RequestMapping("/doAll")
//	public Object doIt() {
//		
//        return git;
//
//	}
}

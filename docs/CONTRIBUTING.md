# Contribution Guidelines

### Fork the project
***
Step by step instructions:
1. Fork the project with the button located in the upper-right corner.
2. Select a folder path to clone your project and navigate to it. <br>
```
  cd <folder_path>
```
3. Clone your project.
```
  git clone <project_path>
```
### Set up the main project requirements
***
Step by step instructions:
1. Make sure you are located in your main/master branch. 
```
  git branch
```
2. Otherwise, navigate to your main/master branch.
```
  git checkout main/master
```
3. Check the remote origin configurated (It should be your own fork).
```
  git remote -v
```
4. Add the remote upstream to the configuration (It should be the main project).
```
  git remote add upstream <project_path>
```
5. Verify that the configuration was set properly.
```
  git remote -v
```
### Create branches in your own fork
***
Step by step instructions:
1. Make sure you are located in your main/master branch. 
```
  git branch
```
2. Otherwise, navigate to your main/master branch.
```
  git checkout master
```
3. Create a new branch with a significant name (representing the component being modified, ex. OSCAR-MAIN-SCREEN-NAVIGATION) and checkout to it.
```
  # Option 1
  git checkout -b <branch-name>
  
  # Option 2
  git branch <branch-name>
  git checkout <branch-name>
```
### Working in the respective branches (Generate short commits)
***
Step by step instructions:
1. Make sure to pull the most recent changes in the main project.
```
  git pull upstream master
```
2. Make sure you are located in your branch. 
```
  git branch
```
3. Otherwise, navigate to your component branch. Naming convention: MYNAME-FUNCTIONALITY
```
  git checkout <branch-name>
```
4. Start making changes at your own pace (check deadlines).
5. Add your changes to the stack.
```
  git add .
```
6. Commit the changes to your local git.
```
  git commit -m "[COMPONENT] Description of the changes made"
```
7. Push your changes to your branch (You can push as many changes as you want within your branch).
```
  git push
```
### Merge changes into the main project (Make sure you merge the right branch and the right changes where performed)
***
Step by step instructions:
1. After performing the first push to your component branch, create a pull request in the main project pointing to your component branch.
2. After creating the pull request, you can still perform as many commits and pushes to your project.
3. After finishing all your changes to that particular component branch, only then you can merge the changes to the main branch.
4. Delete your component branch in git and in your forked repo in github.


## HAPPY CODING! :)

# Git Process

## Set-up
You only need to do this one time. If your git gets messed up you can repeat this to get a fresh start.
```bash
git clone https://github.com/arnavp103/UTT.git  # Download the repo
cd UTT                                          # Change directory to UTT
git branch -r                                   # List all remote branches. The 'origin/HEAD ->' is the default branch

# Do one of the following to set up your local branch:
# If you want to work on a branch that already exists on the remote:
# Replace <branch> with the name of the branch you want to work on
git pull origin <branch>                        # Pull (download) the branch from the remote. It won't be visible with git branch though
git checkout <branch>                           # Swap to the branch.
# Example: git pull origin database; git checkout database

# If you want to create a new branch from a branch:
# Replace <featureName> with the task you're on and <startingBranch> with the name of the branch you want to work on
git checkout -b <featureName>  origin/<startingBranch>      # Create and swap to a local branch from a remote branch
# Example: git checkout -b options origin/search-algorithm
```

## Daily Workflow
These are some general commands that you will probably be using every day.
Note that when you do merges git will ask you to write something, and it will generally pull up the
default editor which is vim. You can fix that by setting git to use a different editor once. Type this into the terminal
```git config --global core.editor "code --wait"```
```bash
# Here you make your changes and add your stuff. You should be pushing to remote after every few hours of work.
git add .                                       	# Add all files to be tracked by git
git commit -m "meaningful present tense message"    # Commit your changes
git push -u origin <featureName>                	# Push your changes to the remote branch. The -u sets the default remote branch you're
                                                	# pushing to of your local branch. So you can just do git push from now on.

# While you do your task someone might have merged to main so you could update your branch with the changes.
git checkout <featureName>                        	# Swap to your branch
git merge main										# Merge the main branch into yours. Might pull up your editor, write a message and save.

# When you're done with your task, you can merge your branch with the main branch.
git checkout main                                   # Switch to the main branch
git pull origin main                                # Pull the latest changes from the remote main branch
git merge <featureName>                             # Merge your branch into the main branch
git push origin main                                # Push the changes to the remote main branch
```


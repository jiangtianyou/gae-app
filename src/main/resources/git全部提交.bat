:: set /p input=please commit info:
git pull 
git add -A
git commit -m "%date%%input%"
git push 
set /p input=Commit Success!
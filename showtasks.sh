#!/usr/bin/env bash

open_chrome(){
   open -a Google\ Chrome "http://localhost:8080/crud/v1/tasks"
}
problem_chrome(){
  echo "There is a problem with Chrome"
}

if ./runcrud.sh; then
  open_chrome
else
    problem_chrome
fi
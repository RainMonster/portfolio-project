FROM clojure
COPY . ~/Desktop/portfolio-project
WORKDIR ~/Desktop/portfolio-project
CMD ["lein", "run"]
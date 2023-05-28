# AAS-By-CRPC



> "Academic Administration System" + "C-RPC"



## Technology Stack

🍤**FE**

- **React** ![](https://img.shields.io/badge/react-18.2.0-brightgreen)
- **antd** ![](https://img.shields.io/badge/antd-5.4.2-orange)
- **mobx** ![](https://img.shields.io/badge/mobx-6.9.0-yellow)
- **node.js** ![](https://img.shields.io/badge/node-18.16.0-green) ![](https://img.shields.io/badge/npm-9.5.1-green)

🍕**BE**

- **SpringBoot** ![](https://img.shields.io/badge/SpringBoot-2.2.12.RELEASE-yellowgreen)
- **etcd** ![](https://img.shields.io/badge/etcd-3.4.7-blue)
- **MySQL** ![](https://img.shields.io/badge/MySQL-5.7.35-blue)
- **Redis** ![](https://img.shields.io/badge/Redis-6.2-red)
- **MongoDB** ![](https://img.shields.io/badge/MongoDB-5.0.2-lightgrey)
- **Docker**



## How to start

🥣**FE**

1. Enter the `"./front-end/system-front"`
2. Type `'yarn'` to install dependencies
3. Type `'yarn start'` to start front-end program

🍖**BE**

1. Enter the `"./docker"`
2. Type `'docker-compose up -d'` to install dependencies
3. Execute scripts `'./mongoInit.sh'` to initialize MongoDB data
4. Import the SQL file(`eas_by_crpc.sql`) under `"./back-end/AAS-By-CRPC-back-end/AAS-web/src/main/resources/database"` path to MySQL
5. Modify the application-dev.yml configuration file for modules `'AAS-data-query-service'` & `'AAS-web'`
6. Go to the `http://localhost:3000/`. Login to administrator account`('123456', '123456')`



## Licence

![](https://img.shields.io/badge/LICENSE-MIT-yellowgreen)

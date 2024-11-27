class CustomError extends Error {
    constructor(err){
        this.name = 'CustomError';
        this.moduleName = err.moduleName;
        this.status = HTTP_STATUS[err.status];
        this.httpStatus = err.status;
        this.date = new Date();
        this.error = err.error;
    }
}
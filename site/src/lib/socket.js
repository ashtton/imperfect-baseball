import ioClient from 'socket.io-client';
const ENDPOINT = 'ws://localhost:3333';

export const socket = ioClient(ENDPOINT);
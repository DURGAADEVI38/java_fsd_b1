import { useState } from 'react'
import reactLogo from './assets/react.svg'
import viteLogo from './assets/vite.svg'
import heroImg from './assets/hero.png'
import './App.css'
import { Route, Routes } from 'react-router-dom'

import UserList from './assets/components/UserList'
import AddUser from './assets/components/AddUser'

function App() {
  const [count, setCount] = useState(0)

  return (
   <Routes>
    <Route path='/users' element={<UserList/>} />
    <Route path='/add' element={<AddUser/>} />
   </Routes>
  )
}

export default App

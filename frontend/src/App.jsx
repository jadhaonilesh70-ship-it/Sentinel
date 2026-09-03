import { useState, useEffect } from 'react';

function App() {
  const [endpoints, setEndpoints] = useState([]);
  const [name, setName] = useState('');
  const [url, setUrl] = useState('');

  const fetchEndpoints = async () => {
    try {
      const res = await fetch('/api/v1/endpoints');
      const data = await res.json();
      setEndpoints(data);
    } catch (err) {
      console.error("Error fetching data", err);
    }
  };

  useEffect(() => {
    fetchEndpoints();
  }, []);

  const handleSubmit = async (e) => {
    e.preventDefault();
    await fetch(/api/v1/endpoints?name= + encodeURIComponent(name) + &url= + encodeURIComponent(url), {
      method: 'POST'
    });
    setName('');
    setUrl('');
    fetchEndpoints();
  };

  return (
    <div className="container mx-auto p-8 max-w-5xl">
      <h1 className="text-4xl font-bold mb-8 text-blue-700">Sentinel Dashboard</h1>
      
      <div className="bg-white p-6 rounded-lg shadow-md mb-8 border border-slate-200">
        <h2 className="text-xl font-semibold mb-4 text-slate-800">Add Endpoint to Monitor</h2>
        <form onSubmit={handleSubmit} className="flex flex-col md:flex-row gap-4">
          <input 
            type="text" placeholder="Name (e.g., Google)" 
            value={name} onChange={(e) => setName(e.target.value)}
            className="border border-slate-300 p-3 rounded-md flex-1 focus:outline-none focus:ring-2 focus:ring-blue-500" required 
          />
          <input 
            type="url" placeholder="URL (e.g., https://google.com)" 
            value={url} onChange={(e) => setUrl(e.target.value)}
            className="border border-slate-300 p-3 rounded-md flex-1 focus:outline-none focus:ring-2 focus:ring-blue-500" required 
          />
          <button type="submit" className="bg-blue-600 text-white px-8 py-3 rounded-md font-bold hover:bg-blue-700 transition-colors">
            Start Monitoring
          </button>
        </form>
      </div>

      <h2 className="text-2xl font-semibold mb-4 text-slate-800">Active Monitors</h2>
      <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
        {endpoints.map(ep => (
          <div key={ep.id} className="bg-white p-5 rounded-lg shadow-sm border-l-4 border-green-500">
            <h3 className="font-bold text-lg mb-1">{ep.name}</h3>
            <p className="text-slate-500 text-sm mb-3 truncate" title={ep.url}>{ep.url}</p>
            <span className={px-3 py-1 text-xs font-bold rounded-full }>
              {ep.active ? 'ACTIVE' : 'PAUSED'}
            </span>
          </div>
        ))}
      </div>
    </div>
  );
}

export default App;
